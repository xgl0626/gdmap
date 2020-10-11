package com.example.gdmap.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.network.LoginRegister
import com.example.gdmap.network.ServiceCreator
import com.example.gdmap.utils.MyApplication
import com.example.gdmap.utils.Toast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

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
    private var registerSharedPreferences: SharedPreferences =
        MyApplication.context.getSharedPreferences("register_account", MODE_PRIVATE)
    val  registerService= ServiceCreator.create(LoginRegister::class.java)
    val loginOrRegisterResult = MutableLiveData<Int>()
    fun login(name: String, password: String) {

        val savedName = registerSharedPreferences.getString("name", "")
        val savedPassword = registerSharedPreferences.getString("password", "")
        if (savedName.equals(name) && savedPassword.equals(password)) {
            //自动登录
            rememberPassword(name, password)
            loginOrRegisterResult.value = 200
            Toast.toast("登录成功")
            Log.d("zt","保存的登录")
        }else{
            //手动登录
            registerService.SiginIn(name,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    if (it.status == 10000) {
                        loginOrRegisterResult.value = 200
                        Log.d("zt","未保存的登录")
                        Toast.toast("登录成功")
                    }else{
                        Toast.toast("输入用户名或者是密码有误，请重新输入")
                    }
                }
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun rememberPassword(name: String, psw: String) {
        Log.d("tagtag", "$name$psw")
        val editor = rememberPassword.edit()
        editor?.apply {
            putString("name", name)
            putString("psw", psw)
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
}