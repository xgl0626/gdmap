package com.example.gdmap.utils

import android.Manifest
import android.net.Uri
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gdmap.R
import com.permissionx.guolindev.PermissionX
import top.limuyang2.photolibrary.LPhotoHelper
import top.limuyang2.photolibrary.util.LPPImageType
import kotlin.math.max


/**
 *@date 2020-8-9
 *@author zhangsan
 *@description图片选择
 */
object ImageSelectutils {
    const val REQUEST_CODE_CHOOSE_PHOTO_ALBUM = 1
    fun selectImageFromAlbum(maxCount: Int = 1, fragment: Fragment) {
        val selectedList = ArrayList<Uri>()
        PermissionX.init(fragment.activity)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    LPhotoHelper.Builder()
                        .maxChooseCount(maxCount) //最多选几个
                        .columnsNumber(3) //每行显示几列图片
                        .imageType(LPPImageType.ofAll()) // 文件类型
                        .pauseOnScroll(false) // 是否滑动暂停加载图片显示
                        .isSingleChoose(false) // 是否是单选
                        .isOpenLastAlbum(false) // 是否直接打开最后一次选择的相册
                        .theme(R.style.LPhotoTheme)
                        .build()
                        .start(fragment, REQUEST_CODE_CHOOSE_PHOTO_ALBUM)
                } else {
                    Toast.toast("访问相册失败，原因：未授权")
                }
            }

    }

    fun selectImageFromAlbum(
        maxCount: Int = 1,
        activity: AppCompatActivity,
        selected: ArrayList<String>?
    ) {
        val selectedList: ArrayList<String>? = if (selected.isNullOrEmpty()) {
            null
        } else {
          selected
        }
        PermissionX.init(activity)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    LPhotoHelper.Builder()
                        .maxChooseCount(maxCount) //最多选几个
                        .columnsNumber(3) //每行显示几列图片
                        .imageType(LPPImageType.ofAll()) // 文件类型
                        .pauseOnScroll(false) // 是否滑动暂停加载图片显示
                        .isSingleChoose(false) // 是否是单选
                        .isOpenLastAlbum(false) // 是否直接打开最后一次选择的相册
                        .selectedPhotos(selectedList)
                        .theme(R.style.LPhotoTheme)
                        .build()
                        .start(activity, REQUEST_CODE_CHOOSE_PHOTO_ALBUM)
                } else {
                    Toast.toast("访问相册失败，原因：未授权")
                }
            }

    }

    fun selectImageFromAlbum(maxCount: Int = 9, activity: AppCompatActivity) {
        PermissionX.init(activity)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    LPhotoHelper.Builder()
                        .maxChooseCount(maxCount) //最多选几个
                        .columnsNumber(3) //每行显示几列图片
                        .imageType(LPPImageType.ofAll()) // 文件类型
                        .pauseOnScroll(false) // 是否滑动暂停加载图片显示
                        .isSingleChoose(false) // 是否是单选
                        .isOpenLastAlbum(false) // 是否直接打开最后一次选择的相册
                        .theme(R.style.LPhotoTheme)
                        .build()
                        .start(activity, REQUEST_CODE_CHOOSE_PHOTO_ALBUM)
                } else {
                    Toast.toast("访问相册失败，原因：未授权")
                }
            }

    }

}
