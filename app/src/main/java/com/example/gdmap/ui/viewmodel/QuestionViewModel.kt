package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.bean.AnswerTestData
import com.example.gdmap.bean.MessagesTestData
import com.example.gdmap.bean.ReplyBean
import com.example.gdmap.network.ServiceCreator
import com.example.gdmap.network.ApiService
import com.example.gdmap.utils.Toast
import okhttp3.MultipartBody
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/*
    servicefragment界面和writequestion界面
 */
class QuestionViewModel : ViewModel() {
    private val isInvalidList = arrayListOf<Boolean>()
    val commentData = MutableLiveData<List<MessagesTestData>>()
    val imageUrls = MutableLiveData<ArrayList<String>>()
    fun getQuestionData() {
        val data = ArrayList<MessagesTestData>()
        data.add(MessagesTestData("xlgxlg"))
        commentData.value = data
    }

    val messageData = MutableLiveData<List<AnswerTestData>>()

    fun getAnswerAndReplyData() {
        val data = ArrayList<AnswerTestData>()
        val replyData = ArrayList<ReplyBean>()
        replyData.add(ReplyBean("xgl"))
        replyData.add(ReplyBean("xgl"))
        replyData.add(ReplyBean("xgl"))
        data.add(AnswerTestData("xlgxlg", replyList = replyData))
        data.add(AnswerTestData("xlgxlg", replyList = replyData))
        data.add(AnswerTestData("xlgxlg", replyList = replyData))
        messageData.value = data
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
        token: String,
        title: String,
        place: String, description: String,
        photoList: List<MultipartBody.Part>
    ) {
        ServiceCreator.create(ApiService::class.java)
            ?.addQuestion(token, title, place, description, photoList)
            ?.subscribeOn(Schedulers.io())
            ?.unsubscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                if (it.status == 10000) {
                    Toast.toast("提问成功")
                } else {
                    Toast.toast("提问失败")
                }
            }
    }

//    fun getQuestionList()
//    {
//        NetWorkUtils.create(ApiService::class.java)
//            ?.search()
//            ?.subscribeOn(Schedulers.io())
//            ?.unsubscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe {
//                if (it.status == 10000) {
//                } else {
//                    Toast.toast("未搜索到该内容")
//                }
//            }
//    }
}