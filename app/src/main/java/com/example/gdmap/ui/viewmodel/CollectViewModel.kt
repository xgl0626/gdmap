package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.bean.QuestionData
import com.example.gdmap.config.TokenConfig
import com.example.gdmap.network.ApiService
import com.example.gdmap.network.ServiceCreator
import com.example.gdmap.utils.LogUtils
import com.example.gdmap.utils.Toast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 *@Date 2020-10-23
 *@Time 20:34
 *@author SpreadWater
 *@description
 */
class CollectViewModel: ViewModel() {
    val imageUrls = MutableLiveData<ArrayList<String>>()
    val collectData = MutableLiveData<List<QuestionData>>()
    private var token2: String? = null

    init {
        token2 = "Bearer ${TokenConfig.token.token}"
    }
    fun getCollectQuestionList(){
        token2?.let {
            ServiceCreator.create(ApiService::class.java)
                .getCollectQuestionList(token2!!)
                .subscribeOn(Schedulers.io())
                ?.unsubscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe {
                    LogUtils.log_d<String>(it.toString())
                    if (it.status == 10000) {
                        collectData.value = it.data
                    } else {
                        Toast.toast("获取收藏失败")
                    }
                }
        }
    }
}