package com.example.gdmap.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.utils.MyApplication
import com.example.gdmap.utils.Toast

/**
 * @Author: 徐国林
 * @ClassName: LoginOrRegisterViewModel
 * @Description:
 * @Date: 2020/9/2 18:02
 */
class LoginOrRegisterViewModel : ViewModel() {
    private var rememberPassword: SharedPreferences = MyApplication.context.getSharedPreferences(
        "remember",
        MODE_PRIVATE
    )

    private var registerSharedPreferences: SharedPreferences =
        MyApplication.context.getSharedPreferences("register_account", MODE_PRIVATE)
    val result = MutableLiveData<Int>()
    fun login(name: String, password: String) {

        val savedName = registerSharedPreferences.getString("name", "")
        val savedPassword = registerSharedPreferences.getString("password", "")
        if (savedName.equals(name) && savedPassword.equals(password)) {
            rememberPassword(name, password)
            result.value = 200
        } else

            Toast.toast("输入用户名或者是密码有误，请重新输入")
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
    fun register(name: String, password: String, password2: String) {
        val editor = registerSharedPreferences.edit()
        editor?.apply {
            putString("name", name)
            putString("password", password)
            putString("password2", password2)
            apply()
        }
        result.value = 200
        Toast.toast("注册成功")
    }
}