package com.example.gdmap.ui.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.bean.UserInfo
import com.example.gdmap.config.TokenConfig
import com.example.gdmap.network.ServiceCreator
import com.example.gdmap.network.UpdateInfo
import com.example.gdmap.utils.Toast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 *@Date 2020/10/11
 *@Time 15:25
 *@author SpreadWater
 *@description
 */
class SetDataViewModel :ViewModel() {
    val updateService=ServiceCreator.create(UpdateInfo::class.java)
    fun updateInfo(user:UserInfo){
        val token2="Bearer "+user.token
        updateService.updateInfo(token2,user.qq,user.tel,user.description,user.nickname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                if (it.status==10000){
                    Toast.toast("更新成功！")
                }else{
                    Log.d("zt",it.data.message)
                    Toast.toast("更新失败!")
                }
            }
    }
}