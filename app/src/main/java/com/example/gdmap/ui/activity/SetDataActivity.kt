package com.example.gdmap.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.bean.UserInfo
import com.example.gdmap.config.TokenConfig
import com.example.gdmap.ui.viewmodel.LoginOrRegisterViewModel
import com.example.gdmap.ui.viewmodel.SetDataViewModel
import com.example.gdmap.utils.AddIconImage
import kotlinx.android.synthetic.main.activity_setdata.*

class SetDataActivity : BaseActivity() {
    private var count = 2
    private var saveData: SharedPreferences? = null
    private var avatar:String=""
    var qq:String=""
    var tel:String=""
    var dcp:String=""
    var Myname:String=""
    private val SetDataViewModel by lazy {
        ViewModelProviders.of(this).get(SetDataViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        et_activity_setdata_data.setText(dcp)
        et_activity_setdata_qq.setText(qq)
        et_activity_setdata_name.setText(Myname)
        et_activity_setdata_phone.setText(tel)
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        avatar=intent.getStringExtra("avatar")
        qq=intent.getStringExtra("qq")
        tel=intent.getStringExtra("tel")
        dcp=intent.getStringExtra("dcp")
        Myname=intent.getStringExtra("name")
        AddIconImage.setImageViewToEditText(R.mipmap.fragment_setdata_go, et_activity_setdata_qq, 1)
        AddIconImage.setImageViewToEditText(
            R.mipmap.fragment_setdata_go,
            et_activity_setdata_phone,
            1
        )
        AddIconImage.setImageViewToEditText(
            R.mipmap.fragment_setdata_go,
            et_activity_setdata_name,
            1
        )
        AddIconImage.setImageViewToEditText(
            R.mipmap.fragment_setdata_go,
            et_activity_setdata_data,
            1
        )
        setCanNotEditNoClick(et_activity_setdata_data)
        setCanNotEditNoClick(et_activity_setdata_qq)
        setCanNotEditNoClick(et_activity_setdata_name)
        setCanNotEditNoClick(et_activity_setdata_phone)
        bt_activity_setdata_setData.setOnClickListener {
            if (count % 2 == 0) {
                setCanEdit(et_activity_setdata_data)
                setCanEdit(et_activity_setdata_qq)
                setCanEdit(et_activity_setdata_name)
                setCanEdit(et_activity_setdata_phone)
                bt_activity_setdata_setData.text = "保存"
            } else {
                setCanNotEditNoClick(et_activity_setdata_data)
                setCanNotEditNoClick(et_activity_setdata_qq)
                setCanNotEditNoClick(et_activity_setdata_name)
                setCanNotEditNoClick(et_activity_setdata_phone)
                bt_activity_setdata_setData.text = "编辑"
                val description=et_activity_setdata_data.text.toString()
                val qq=et_activity_setdata_qq.text.toString()
                val name=et_activity_setdata_name.text.toString()
                val phone=et_activity_setdata_phone.text.toString()
                if (et_activity_setdata_data.text != null ||
                    et_activity_setdata_name.text != null ||
                    et_activity_setdata_qq.text != null || et_activity_setdata_phone.text != null){
                    val UpdateUser= UserInfo(description,name,qq,phone,TokenConfig.token.token)
                    SetDataViewModel.updateInfo(UpdateUser)
                    finish()
                }
            }
            count++
        }
    }

    override fun initClick() {

    }

    override fun initData() {
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_setdata
    }

    private fun setCanNotEditNoClick(editText: EditText) {
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false
    }

    private fun setCanEdit(editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
    }

}