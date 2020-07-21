package com.example.gdmap.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.ui.fragment.MyFragment
import com.example.gdmap.utils.ImmersedStatusbarUtils
import kotlinx.android.synthetic.main.activity_article_content.*
import kotlinx.android.synthetic.main.activity_article_content.toolBar
import kotlinx.android.synthetic.main.activity_setdata.*
import kotlinx.android.synthetic.main.activity_weather.*

class SetDataActivity :BaseActivity(){
    private var count=2
    private var saveData: SharedPreferences?=null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setdata)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用
        ImmersedStatusbarUtils.initSetContentView(this,toolBar)
        initView()
    }

    override fun onStart() {
        super.onStart()
        et_activity_setdata_data.setText(saveData?.getString("data",""))
        et_activity_setdata_name.setText(saveData?.getString("name",""))
        et_activity_setdata_qq.setText(saveData?.getString("qq",""))
        et_activity_setdata_phone.setText(saveData?.getString("tel",""))
    }
    private fun initView() {
        saveData=getSharedPreferences("userdata", Context.MODE_PRIVATE)
        setImageViewAndButton(R.mipmap.fragment_setdata_go,et_activity_setdata_qq)
        setImageViewAndButton(R.mipmap.fragment_setdata_go,et_activity_setdata_phone)
        setImageViewAndButton(R.mipmap.fragment_setdata_go,et_activity_setdata_name)
        setImageViewAndButton(R.mipmap.fragment_setdata_go,et_activity_setdata_data)
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
    fun setCanNotEditNoClick(editText: EditText) {
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false
        // 如果之前没设置过点击事件，该处可省略
//        editText.setOnClickListener(null)
    }

    fun setCanEdit(editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
    }


    private fun setImageViewAndButton(drawable: Int, view: TextView) {
        val drawable: Drawable =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                resources.getDrawable(drawable, null)
            } else {
                TODO("VERSION.SDK_INT < LOLLIPOP")
            }
        drawable.setBounds(0, 0, 80, 80)
        view.setCompoundDrawables(null, null,drawable ,null)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home)
        {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}