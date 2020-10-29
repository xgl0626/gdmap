package com.example.gdmap.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.bean.MyUser
import com.example.gdmap.network.ApiService
import com.example.gdmap.network.ServiceCreator
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 *@Date 2020/10/11
 *@Time 16:11
 *@author SpreadWater
 *@description
 */
class MyViewModel : ViewModel() {
    val MyInfoService = ServiceCreator.create(ApiService::class.java)
    val MyInfoResultValue = MutableLiveData<MyUser>()
    fun getUserInfo(token: String) {
        val token2 = "Bearer $token"
        MyInfoService.getInfo(token2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.status == 10000) {
                    val data = it.data
                    val user = MyUser(
                        data.user_id,
                        data.nickname,
                        data.description,
                        data.qq,
                        data.tel,
                        data.user_status,
                        data.avatar
                    )
                    MyInfoResultValue.value = user
                    Log.d("zt", "获取信息成功！")
                } else {
                    Log.d("zt", "获取信息失败！")
                    Log.d("zt", it.status.toString() + it.info)
                }
            }
    }

}