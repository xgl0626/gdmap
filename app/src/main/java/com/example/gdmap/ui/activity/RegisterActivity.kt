package com.example.gdmap.ui.activity


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.gdmap.MainActivity
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.network.LoginRegister
import com.example.gdmap.network.ServiceCreator
import com.example.gdmap.ui.viewmodel.LoginOrRegisterViewModel
import com.example.gdmap.utils.AddIconImage

import com.example.gdmap.utils.Toast
import com.example.gdmap.utils.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_rigister.*
import kotlinx.android.synthetic.main.activity_rigister.iv_logo
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RegisterActivity : BaseActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LoginOrRegisterViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_rigister
    }

    override fun initData() {
        viewModel.loginOrRegisterResult.observe(this, Observer {
            if (it == 200) {
                changeToActivity(MainActivity())
                finish()
            }
        })
    }

    override fun initView() {
        AddIconImage.setImageViewToEditText(
            R.mipmap.acticity_login_name,
            et_activity_register_username,
            0
        )
        AddIconImage.setImageViewToEditText(
            R.mipmap.activity_login_psw,
            et_activity_register_psw,
            0
        )
    }

    override fun initClick() {
        bt_activity_register_register.setOnSingleClickListener {
            val username = et_activity_register_username.text.toString()
            val psw = et_activity_register_psw.text.toString()
            if (username != "" && psw != "")
                viewModel.register(username, psw)
            else
                Toast.toast("用户名或者密码不能为空")
        }
    }

    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

}