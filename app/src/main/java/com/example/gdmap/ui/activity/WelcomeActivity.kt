package com.example.gdmap.ui.activity

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.View.TRANSLATION_X
import android.view.View.TRANSLATION_Y
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.utils.ImmersedStatusbarUtils

class WelcomeActivity : BaseActivity() {
    private  var view: View? = null
    private  var img:ImageView?=null
            @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = View.inflate(this, R.layout.activity_welcome, null)
        setContentView(view)
        ImmersedStatusbarUtils.initSetContentView(this, null)
        initView()
    }

    private fun initView() {
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