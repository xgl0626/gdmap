package com.example.gdmap.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.annotation.RequiresApi
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity

class WelcomeActivity : BaseActivity() {
    companion object {
        const val LOGIN = 0
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendMsg(LOGIN)
    }

    override fun initView() {

    }

    override fun initClick() {

    }

    override fun initData() {

    }

    override fun getViewLayout(): Int {
        return R.layout.activity_welcome
    }

    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
        finish()
    }

    fun sendMsg(index: Int) {
        val message = Message()
        message.what = index
        handler.sendMessageDelayed(message, 2000)
    }

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0)
                changeToActivity(LoginActivity())
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(
            R.anim.common_scale_fade_in_with_bezier,
            R.anim.common_scale_fade_out_with_bezier
        )
    }
}