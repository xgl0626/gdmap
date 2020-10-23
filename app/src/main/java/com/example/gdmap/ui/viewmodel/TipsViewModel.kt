package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.bean.QuestionData
import com.example.gdmap.bean.Tip
import com.example.gdmap.network.ApiService
import com.example.gdmap.network.ServiceCreator
import com.example.gdmap.utils.LogUtils
import com.example.gdmap.utils.Toast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 *@Date 2020-10-23
 *@Time 21:36
 *@author SpreadWater
 *@description
 */
class TipsViewModel :ViewModel() {
    val tipData = MutableLiveData<List<Tip>>()
    fun getTips(tittle:String){
        ServiceCreator.create(ApiService::class.java)
            .getTips(tittle)
            .subscribeOn(Schedulers.io())
            ?.unsubscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                LogUtils.log_d<String>(it.toString())
                if (it.status == 10000) {
                    tipData.value = it.data
                } else {
                    Toast.toast("获取小贴士失败")
                }
            }
    }
}