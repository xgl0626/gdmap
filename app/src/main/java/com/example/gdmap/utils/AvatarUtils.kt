package com.example.gdmap.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*

object AvatarUtils {
    const val TAKE_PHOTO = 1 //拍照

    const val CHOOSE_PHOTO = 2 //选择相册

    const val REQUEST_CODE_CAMERA = 3 //相机权限请求

    const val REQUEST_CODE_ALBUM = 4 //相册权限请求

    var imageUri //相机拍照图片保存地址
            : Uri? = null

    /**
     * 选择图片，从图库、相机
     *
     * @param activity 上下文
     */
    fun choicePhoto(activity: Activity) {
        //采用的是系统Dialog作为选择弹框
        AlertDialog.Builder(activity).setTitle("上传头像") //设置对话框标题
            .setPositiveButton("拍照") { dialog, which ->

                //添加确定按钮
                if (Build.VERSION.SDK_INT >= 23) { //检查相机权限
                    val permissions =
                        ArrayList<String>()
                    if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        permissions.add(Manifest.permission.CAMERA)
                    }
                    if (permissions.size == 0) { //有权限,跳转
                        //打开相机-兼容7.0
                        openCamera(activity as Activity)
                    } else {
                        activity.requestPermissions(
                            permissions.toTypedArray(),
                            REQUEST_CODE_CAMERA
                        )
                    }
                } else {
                    //打开相机-兼容7.0
                    openCamera(activity)
                }
            }.setNegativeButton(
                "系统相册"
            ) { dialog, which -> //如果有权限申请，请在Activity中onRequestPermissionsResult权限返回里面重新调用openAlbum()
                if (ContextCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_CODE_ALBUM
                    )
                } else {
                    openAlbum(activity)
                }
            }.show() //在按键响应事件中显示此对话框
    }

    /**
     * 打开相机
     * 兼容7.0
     *
     * @param activity
     */
    fun openCamera(activity: Activity) {
        // 创建File对象，用于存储拍照后的图片
        val outputImage =
            File(activity.externalCacheDir, "output_image.jpg")
        try {
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        imageUri = if (Build.VERSION.SDK_INT < 24) {
            Uri.fromFile(outputImage)
        } else {
            //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
            //参数二:fileprovider绝对路径 com.dyb.testcamerademo：项目包名
            FileProvider.getUriForFile(
                activity,
                "com.example.gdmap.fileprovider",
                outputImage
            )
        }
        // 启动相机程序
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        activity.startActivityForResult(intent, TAKE_PHOTO)
    }

    /**
     * 打开图库
     *
     * @param activity
     */
    fun openAlbum(activity: Activity) {
        //调用系统图库的意图
        val choosePicIntent = Intent(Intent.ACTION_PICK, null)
        choosePicIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        activity.startActivityForResult(choosePicIntent, CHOOSE_PHOTO)
    }

    /**
     * 得到byte[]
     * 这里对传入的图片Uri压缩到1M以内，并转换为byte[]后返回
     *
     * @param activity 上下文
     * @param uri      传入图片的Uri
     * @return byte[]
     */
    @Throws(IOException::class)
    fun getImgByteFromUri(activity: Activity, uri: Uri?): ByteArray? {
        val bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, uri)
        val out = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) //100表示不压缩，直接放到out里面
        var options = 90 //压缩比例
        while (out.toByteArray().size / 1024 > 200) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            out.reset() // 重置baos即清空baos
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                options,
                out
            ) // 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10 // 每次都减少10
        }
        return out.toByteArray()
    }
}