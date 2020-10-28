package com.example.gdmap.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.*
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.config.TokenConfig.BASE_URL
import com.example.gdmap.ui.viewmodel.QuestionViewModel
import com.example.gdmap.utils.ImageSelectutils
import com.example.gdmap.utils.MyApplication.Companion.context
import com.example.gdmap.utils.Toast
import kotlinx.android.synthetic.main.activity_load_disaster.*
import top.limuyang2.photolibrary.LPhotoHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * @Author: xgl
 * @ClassName: LoadDisasterActivity
 * @Description:
 * @Date: 2020/10/26 13:45
 */

class LoadDisasterActivity : BaseActivity() {
    private var dialog: ProgressDialog? = null

    companion object {
        const val MAX_SELECTABLE_IMAGE_COUNT = 2
    }

    private val viewModel by lazy { ViewModelProviders.of(this).get(QuestionViewModel::class.java) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setSupportActionBar(toolbar)
    }

    override fun initClick() {
        bt_send_images.setOnClickListener {
            sendMsg(0)
            viewModel.redirect()
        }
    }

    fun sendMsg(index: Int) {
        val message = Message()
        message.what = index
        handler.sendMessage(message)
    }

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    dialog = ProgressDialog(this@LoadDisasterActivity)
                    dialog?.setMessage("加载数据中，由于模型较大，请耐心等待...")
                    dialog?.show()
                }
                1 -> {
                    dialog?.dismiss()
                }
            }

        }
    }

    override fun initData() {
        initAddImageView()
        viewModel.redirectData.observe(this, Observer {
            sendMsg(1)
            Glide.with(iv_load_disaster).load(BASE_URL + it.photo_url).into(iv_load_disaster)
        })
    }

    private fun initAddImageView() {

        nine_grid_view.addView(
            ContextCompat.getDrawable(
                this,
                R.drawable.qa_ic_quiz_add_picture_empty
            )?.let { createImageViewFromVector(it) })
        nine_grid_view.setOnItemClickListener { _, index ->
            if (index == nine_grid_view.childCount - 1) {
                ImageSelectutils.selectImageFromAlbum(
                    MAX_SELECTABLE_IMAGE_COUNT,
                    this
                )
            } else {
                Toast.toast("最多只能添加2张图片哦")
            }
        }
        viewModel.imageUrls.observe(this, Observer { selectedImageFiles ->
            selectedImageFiles ?: return@Observer
            viewModel.resetInvalid()
            //对view进行复用
            for (i in 0 until nine_grid_view.childCount - 1) {
                val view = nine_grid_view.getChildAt(i)
                if (i == nine_grid_view.childCount - 1) {
                    //保留添加图标
                    break
                } else if (i >= selectedImageFiles.size) {
                    //移除多出来的view
                    for (j in i until nine_grid_view.childCount - 1)
                        nine_grid_view.removeViewAt(i)
                    continue
                }
                if (selectedImageFiles[i].isNotEmpty()) {
                    (view as ImageView).setImageURI(Uri.parse(selectedImageFiles[i]))
                    viewModel.checkInvalid(false)
                } else viewModel.checkInvalid(true)

            }
            //补充缺少的view
            selectedImageFiles.asSequence()
                .filterIndexed { index, _ -> index >= nine_grid_view.childCount - 1 }
                .forEach {
                    if (it.isNotEmpty()) {
                        nine_grid_view.addView(
                            createImageView(Uri.parse(it)),
                            nine_grid_view.childCount - 1
                        )
                        viewModel.checkInvalid(false)
                    } else viewModel.checkInvalid(true)
                }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ImageSelectutils.REQUEST_CODE_CHOOSE_PHOTO_ALBUM -> {
                val imageListUri =
                    ArrayList((LPhotoHelper.getSelectedPhotos(data))).map {
                        it.toString()
                    }
                val imageListAbsolutePath = ArrayList<String>()
                imageListUri.forEach { imageListAbsolutePath.add(it) }
                viewModel.setImageList(imageListAbsolutePath)
            }
        }
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_load_disaster
    }

    private fun createImageViewFromVector(drawable: Drawable) = ImageView(this).apply {
        scaleType = ImageView.ScaleType.CENTER
        background = ContextCompat.getDrawable(
            this@LoadDisasterActivity,
            R.drawable.shape_quiz_select_pic_empty_background
        )
        setImageDrawable(drawable)
    }

    private fun createImageView(uri: Uri) = ImageView(this).apply {
        scaleType = ImageView.ScaleType.CENTER_CROP
        setImageURI(uri)
    }
}