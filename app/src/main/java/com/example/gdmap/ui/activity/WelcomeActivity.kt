package com.example.gdmap.ui.activity

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity

class WelcomeActivity : BaseActivity() {
            @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        val img=findViewById<ImageView>(R.id.iv_activity_welcome_img)
        val objectAnimatorX=ObjectAnimator.ofFloat(img,"translationX",0F,250F)
        val objectAnimatorY=ObjectAnimator.ofFloat(img,"translationY",0F,-1000F)
        objectAnimatorX.duration=900
        objectAnimatorY.duration=900
        objectAnimatorX.interpolator=FastOutLinearInInterpolator()
        objectAnimatorY.interpolator=FastOutLinearInInterpolator()
        objectAnimatorX.start()
        objectAnimatorY.start()
        handler.sendMessageDelayed(Message.obtain(), 1000)
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
    }
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            changeToActivity(LoginActivity())
            finish()
        }
    }

}