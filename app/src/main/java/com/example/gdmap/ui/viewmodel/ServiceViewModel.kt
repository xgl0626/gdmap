package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.database.MessagesTestData

class ServiceViewModel : ViewModel() {
    val homeMessageData = MutableLiveData<List<MessagesTestData>>()

    fun getHomeData() {
        val data = ArrayList<MessagesTestData>()
        data.add(MessagesTestData("xlgxlg"))
        homeMessageData.value = data
    }

}