package com.example.gdmap.ui.fragment

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gdmap.R
import com.example.gdmap.base.BaseFragment
import com.example.gdmap.ui.activity.LoginActivity
import com.example.gdmap.ui.activity.SetDataActivity
import com.example.gdmap.ui.activity.SignActivity
import com.example.gdmap.utils.AddIconImage
import com.example.gdmap.utils.AvatarUtils
import com.example.gdmap.utils.pressToZoomOut
import kotlinx.android.synthetic.main.fragment_me.*

class MyFragment : BaseFragment(),View.OnClickListener{
    private var saveData: SharedPreferences?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_me,container,false)
    }
    override fun initView() {
        saveData=context?.getSharedPreferences("userdata",MODE_PRIVATE)
        bt_fragment_me_data.setOnClickListener(this)
        bt_fragment_me_question.setOnClickListener(this)
        bt_fragment_me_set.setOnClickListener(this)
        bt_fragment_me_day.setOnClickListener(this)
        iv_fragment_me_user_avator.setOnClickListener(this)
        AddIconImage.setImageViewToButton(R.mipmap.fragment_me_bt_day,bt_fragment_me_day,0)
        AddIconImage.setImageViewToButton(R.mipmap.fragment_me_bt_set,bt_fragment_me_set,0)
        AddIconImage.setImageViewToButton(R.mipmap.fragment_me_bt_question,bt_fragment_me_question,0)
        AddIconImage.setImageViewToButton(R.mipmap.acticity_login_name,bt_fragment_me_data,0)
    }

    override fun initClick() {
        tv_back_login.setOnClickListener {
            tv_back_login.text = ""
            pressToZoomOut(it)
            changeToActivity(LoginActivity())
        }
    }

    override fun initData() {
    }

    override fun onClick(view: View?) {
        when(view?.id)
        {
            R.id.bt_fragment_me_data->changeToActivity2(SetDataActivity())
            R.id.bt_fragment_me_day->changeToActivity(SignActivity())
            R.id.iv_fragment_me_user_avator->{
                AvatarUtils.choicePhoto(context as Activity)
        }
        }
    }
    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this.activity, activity::class.java)
        startActivity(intent)
    }
    private fun changeToActivity2(activity: Activity) {
        val intent = Intent(this.activity, activity::class.java)
        startActivity(intent)
    }
    override fun onStart() {
        tv_fragment_me_user_data.text = saveData?.getString("data","")
        tv_fragment_me_user_name.text = saveData?.getString("name","")
        super.onStart()
    }
}