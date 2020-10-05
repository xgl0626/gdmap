package com.example.gdmap.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.utils.AddIconImage
import kotlinx.android.synthetic.main.activity_setdata.*

class SetDataActivity :BaseActivity(){
    private var count=2
    private var saveData: SharedPreferences?=null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        et_activity_setdata_data.setText(saveData?.getString("data",""))
        et_activity_setdata_name.setText(saveData?.getString("name",""))
        et_activity_setdata_qq.setText(saveData?.getString("qq",""))
        et_activity_setdata_phone.setText(saveData?.getString("tel",""))
    }
    override fun initView() {
        setSupportActionBar(toolbar)
        saveData=getSharedPreferences("userdata", Context.MODE_PRIVATE)
        AddIconImage.setImageViewToEditText(R.mipmap.fragment_setdata_go,et_activity_setdata_qq,1)
        AddIconImage.setImageViewToEditText(R.mipmap.fragment_setdata_go,et_activity_setdata_phone,1)
        AddIconImage.setImageViewToEditText(R.mipmap.fragment_setdata_go,et_activity_setdata_name,1)
        AddIconImage.setImageViewToEditText(R.mipmap.fragment_setdata_go,et_activity_setdata_data,1)
        setCanNotEditNoClick(et_activity_setdata_data)
        setCanNotEditNoClick(et_activity_setdata_qq)
        setCanNotEditNoClick(et_activity_setdata_name)
        setCanNotEditNoClick(et_activity_setdata_phone)
        bt_activity_setdata_setData.setOnClickListener {
            if(count%2==0) {
                setCanEdit(et_activity_setdata_data)
                setCanEdit(et_activity_setdata_qq)
                setCanEdit(et_activity_setdata_name)
                setCanEdit(et_activity_setdata_phone)
                bt_activity_setdata_setData.text = "保存"
            }
            else
            {
                setCanNotEditNoClick(et_activity_setdata_data)
                setCanNotEditNoClick(et_activity_setdata_qq)
                setCanNotEditNoClick(et_activity_setdata_name)
                setCanNotEditNoClick(et_activity_setdata_phone)
                bt_activity_setdata_setData.text = "编辑"
                val editor=saveData?.edit()
                editor?.putString("name",et_activity_setdata_name.text.toString())
                editor?.putString("qq",et_activity_setdata_qq.text.toString())
                editor?.putString("tel",et_activity_setdata_phone.text.toString())
                editor?.putString("data",et_activity_setdata_data.text.toString())
                editor?.commit()
                finish()
            }
            count++
        }
    }

    override fun initClick() {

    }

    override fun initData() {
    }

    override fun getViewLayout(): Int{
        return R.layout.activity_setdata
    }

    private fun setCanNotEditNoClick(editText: EditText) {
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false
        // 如果之前没设置过点击事件，该处可省略
//        editText.setOnClickListener(null)
    }

    private fun setCanEdit(editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
    }

}