package com.example.gdmap.ui.viewmodel

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.bean.QuestionData
import com.example.gdmap.config.TokenConfig.token
import com.example.gdmap.network.ApiService
import com.example.gdmap.network.ServiceCreator
import com.example.gdmap.utils.LogUtils
import com.example.gdmap.utils.MyApplication.Companion.context
import com.example.gdmap.utils.Toast
import com.example.gdmap.utils.getFilePathFromContentUri
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File


/*
    servicefragment界面和writequestion界面
 */
class QuestionViewModel : ViewModel() {
    private val isInvalidList = arrayListOf<Boolean>()
    val commentData = MutableLiveData<List<QuestionData>>()
    val imageUrls = MutableLiveData<ArrayList<String>>()
    private var token2: String? = null

    init {
        token2 = "Bearer ${token.token}"
    }

    fun getQuestionListData(page: Int = 1) {
        token2?.let {
            ServiceCreator.create(ApiService::class.java)
                .getQuestionList(it, page)
                .subscribeOn(Schedulers.io())
                ?.unsubscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe {
                    LogUtils.log_d<String>(it.toString())
                    if (it.status == 10000) {
                        commentData.value = it.data
                    } else {
                        Toast.toast("获取问题失败")
                    }
                }
        }
    }

    fun checkInvalid(b: Boolean) {
        isInvalidList.add(b)
    }

    fun resetInvalid() {
        isInvalidList.clear()
    }

    fun setImageList(imageList: ArrayList<String>) {
        imageUrls.value = imageList
    }

    fun addQuestion(
        title: String,
        place: String,
        description: String
    ) {
        val builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("tittle", title)
            .addFormDataPart("place", place)
            .addFormDataPart("description", description)
        if (!imageUrls.value.isNullOrEmpty()) {
            val files = imageUrls.value!!.asSequence()
                .map { File(getFilePathFromContentUri(it.toUri(), context.contentResolver)) }
                .toList()
            files.forEachIndexed { index, file ->
                val suffix = file.name.substring(file.name.lastIndexOf(".") + 1)
                val mediaType = MediaType.parse("image/$suffix")
                val imageBody = RequestBody.create(mediaType, file)
                val name = "photo" + (index + 1)
                builder.addFormDataPart(name, file.name, imageBody)
            }
        }
        token2?.let {
            ServiceCreator.create(ApiService::class.java)
                .addQuestion(it, builder.build().parts())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    it.printStackTrace()
                }
                .subscribe {
                    Log.d("question", it.toString())
                    if (it.status == 10000) {
                        Toast.toast("提问成功")
                    } else {
                        Toast.toast("提问失败")
                    }
                }
        }
    }

//    fun getFilePathFromContentUri(
//        contentUri: Uri?,
//        contentResolver: ContentResolver
//    ): String? {
//        val filePath: String
//        val filePathColumn = arrayOf(MediaStore.MediaColumns.DATA)
//        val cursor = contentResolver.query(contentUri!!, filePathColumn, null, null, null)
//        cursor!!.moveToFirst()
//        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
//        filePath = cursor.getString(columnIndex)
//        cursor.close()
//        return filePath
//    }
}