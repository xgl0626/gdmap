package com.example.gdmap.ui.activity


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gdmap.MainActivity
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.ui.viewmodel.LoginOrRegisterViewModel
import com.example.gdmap.utils.AddIconImage

import com.example.gdmap.utils.Toast
import kotlinx.android.synthetic.main.activity_rigister.*

class RegisterActivity :BaseActivity(),View.OnClickListener
{
    private val viewModel by lazy { ViewModelProviders.of(this).get(LoginOrRegisterViewModel::class.java) }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_rigister
    }

    override fun initData() {
        viewModel.loginOrRegisterResult.observe(this, Observer {
            if(it==200)
            {
                changeToActivity(MainActivity())
            }
        })
    }

    override fun initView() {
        AddIconImage.setImageViewToEditText(R.mipmap.acticity_login_name, et_activity_register_username, 0)
        AddIconImage.setImageViewToEditText(R.mipmap.activity_login_psw, et_activity_register_psw, 0)
        bt_activity_register_register.setOnClickListener(this)
    }

    override fun initClick() {
    }

    override fun onClick(view: View?) {
        val username=et_activity_register_username.text.toString()
        val psw=et_activity_register_psw.text.toString()
        when(view?.id)
        {
            R.id.bt_activity_register_register->{
                if(username!="" && psw!="")
                    viewModel.register(username,psw)
                else
                    Toast.toast("用户名或者密码不能为空")
            }
            }
        }

    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }
}