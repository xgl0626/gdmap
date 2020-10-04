package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.database.AnswerTestData
import com.example.gdmap.database.MessagesTestData

class ServiceViewModel : ViewModel() {
    val commentData = MutableLiveData<List<MessagesTestData>>()

    fun getHomeData() {
        val data = ArrayList<MessagesTestData>()
        data.add(MessagesTestData("xlgxlg"))
        commentData.value = data
    }
    val messageData = MutableLiveData<List<AnswerTestData>>()

    fun getAnswerData() {
        val data = ArrayList<AnswerTestData>()
        data.add(AnswerTestData("xlgxlg"))
        data.add(AnswerTestData("xlgxlg"))
        data.add(AnswerTestData("xlgxlg"))
        messageData.value = data
    }
}