package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.bean.AnswerTestData
import com.example.gdmap.bean.MessagesTestData
import com.example.gdmap.bean.ReplyBean

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
}