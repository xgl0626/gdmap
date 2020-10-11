package com.example.gdmap.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.network.LoginRegister
import com.example.gdmap.network.ServiceCreator
import com.example.gdmap.utils.MyApplication
import com.example.gdmap.utils.Toast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.math.log

/**
 * @Author: xgl
 * @ClassName: LoginOrRegisterViewModel
 * @Description:登录和注册界面网络请求事件处理
 * @Date: 2020/9/2 18:02
 */
class LoginOrRegisterViewModel : ViewModel() {
    private var rememberPassword: SharedPreferences = MyApplication.context.getSharedPreferences(
        "remember",
        MODE_PRIVATE
    )
    val  registerService= ServiceCreator.create(LoginRegister::class.java)
    val loginOrRegisterResult = MutableLiveData<Int>()
    fun login(name: String, password: String) {
        registerService.SiginIn(name,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                if (it.status == 10000) {
                    rememberPassword(name, password,it.data.token)
                    loginOrRegisterResult.value = 200
                    Toast.toast("登录成功")
                }else
                    Toast.toast("输入用户名或者是密码有误，请重新输入")

        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun rememberPassword(name: String, psw: String,token:String) {
        Log.d("tagtag", "$name$psw")
        val editor = rememberPassword.edit()
        editor?.apply {
            putString("name", name)
            putString("psw", psw)
            putString("token",token)
            apply()
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun register(name: String, password: String) {
            registerService.SignUp(name,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.status == 10000) {
                        loginOrRegisterResult.value = 200
                        Toast.toast("注册成功")
                    }else{
                        Toast.toast("注册失败")
                    }
                }
    }
    companion object{
       const val TAG="ZT"
    }
}