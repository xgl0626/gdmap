package com.example.gdmap.ui.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.config.TokenConfig.token
import com.example.gdmap.ui.viewmodel.QuestionViewModel
import com.example.gdmap.utils.ImageSelectutils
import com.example.gdmap.utils.ImageSelectutils.REQUEST_CODE_CHOOSE_PHOTO_ALBUM
import com.example.gdmap.utils.Toast
import com.example.gdmap.utils.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_comment.nine_grid_view
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_tips.toolbar
import top.limuyang2.photolibrary.LPhotoHelper
import java.io.File

/**
 * @Author: xgl
 * @ClassName: WriteQuestionActivity
 * @Description:
 * @Date: 2020/10/5 21:34
 */
class WriteQuestionActivity : BaseActivity() {
    companion object {
        const val MAX_SELECTABLE_IMAGE_COUNT = 5
    }

    private val photoList = ArrayList<File>()
    private val viewModel by lazy { ViewModelProviders.of(this).get(QuestionViewModel::class.java) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initView() {
        setSupportActionBar(toolbar)
    }

    override fun initClick() {
        Log.d("question", token.toString())
        bt_send_answer.setOnSingleClickListener {
            val title = et_question_title.text.toString()
            val content = et_question_content.text.toString()
            viewModel.addQuestion(
                title,
                "重庆",
                content
            )
            finish()
        }
    }

    override fun initData() {
        initAddImageView()

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
                Toast.toast("最多只能添加5张图片哦")
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
            REQUEST_CODE_CHOOSE_PHOTO_ALBUM -> {
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
        return R.layout.activity_question
    }

    private fun createImageViewFromVector(drawable: Drawable) = ImageView(this).apply {
        scaleType = ImageView.ScaleType.CENTER
        background = ContextCompat.getDrawable(
            this@WriteQuestionActivity,
            R.drawable.shape_quiz_select_pic_empty_background
        )
        setImageDrawable(drawable)
    }

    private fun createImageView(uri: Uri) = ImageView(this).apply {
        scaleType = ImageView.ScaleType.CENTER_CROP
        setImageURI(uri)
    }

}