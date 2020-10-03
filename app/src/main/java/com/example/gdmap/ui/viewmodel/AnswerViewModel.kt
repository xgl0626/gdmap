package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.gdmap.database.AnswerTestData

class AnswerViewModel : ViewModel() {

    val messageData = MutableLiveData<List<AnswerTestData>>()

    fun getMessageData() {
        val data = ArrayList<AnswerTestData>()
        data.add(AnswerTestData("xlgxlg"))
        messageData.value = data
    }
}