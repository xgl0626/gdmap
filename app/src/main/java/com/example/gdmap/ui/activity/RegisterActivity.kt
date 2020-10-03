package com.example.gdmap.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.gdmap.MainActivity
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.utils.AddIconImage
import com.example.gdmap.utils.ImmersedStatusbarUtils
import com.example.gdmap.utils.Toast
import kotlinx.android.synthetic.main.activity_rigister.*
import kotlinx.android.synthetic.main.activity_rigister.textView2

class RegisterActivity :BaseActivity(),View.OnClickListener
{
    private var registerSharedPreferences:SharedPreferences?=null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rigister)
        initView()
        ImmersedStatusbarUtils.initSetContentView(this,textView2)
    }

    private fun initView() {
        AddIconImage.setImageViewToEditText(R.mipmap.acticity_login_name, et_activity_register_username, 0)
        AddIconImage.setImageViewToEditText(R.mipmap.activity_login_psw, et_activity_register_psw, 0)
        bt_activity_register_register.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val username=et_activity_register_username.text.toString()
        val psw=et_activity_register_psw.text.toString()
        when(view?.id)
        {
            R.id.bt_activity_register_register->{
                if(username!="" && psw!="")
                    postUser(username,psw)
                else
                    Toast.toast("用户名或者密码不能为空")
            }
            }
        }

    private fun postUser(username:String, psw1:String) {
        registerSharedPreferences=getSharedPreferences("register_account", Context.MODE_PRIVATE)
        val editor=registerSharedPreferences?.edit()
        editor?.putString("name",username)
        editor?.putString("password",psw1)
        editor?.apply()
        Toast.toast("注册成功")
        changeToActivity(MainActivity())
    }

    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }
}