package com.example.gdmap.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.view.KeyEvent
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.utils.Toast
import kotlinx.android.synthetic.main.activity_sign.*

class SignActivity() : BaseActivity() {
    private var currentDay: String? = null
    private var count = 0
    private var getDay: String? = null
    private var saveDay: SharedPreferences? = null
    private var saveCounts: SharedPreferences? = null

    constructor(parcel: Parcel) : this() {
        currentDay = parcel.readString()
        count = parcel.readInt()
        getDay = parcel.readString()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        count = saveCounts?.getInt("daycount", 0)!!
        tv_activity_sign_daycounts.text = count.toString()
        tv_seekbar.text = "$count%"
        runOnUiThread {
            sk_activity_sign.progress = count
        }
        super.onStart()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return false
        }
        return false
    }

    @SuppressLint("ResourceAsColor")
    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true) //设置返回键可用
        val time = android.text.format.Time()
        time.setToNow()
        currentDay = time.monthDay.toString()
        saveCounts = getSharedPreferences("count", Context.MODE_PRIVATE)
        saveDay = getSharedPreferences("Today", Context.MODE_PRIVATE)
        val editor2: SharedPreferences.Editor? = saveDay?.edit()
        bt_activity_sign.setOnClickListener { view ->
            getDay = saveDay?.getString("today", "")
            if (getDay == currentDay) {
                Toast.toast("明天再来吧")
            } else {
                runOnUiThread {
                    sk_activity_sign.progress = count
                }
                count++
                tv_seekbar.text = "$count%"
                tv_activity_sign_daycounts.text = count.toString()
                bt_activity_sign.setBackgroundColor(R.color.blue)
                editor2?.putString("today", currentDay)
                editor2?.apply()
                Toast.toast("签到成功")

            }
        }
    }

    override fun initClick() {
    }

    override fun initData() {
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_sign
    }

    override fun onDestroy() {
        val editor: SharedPreferences.Editor? = saveCounts?.edit()
        editor?.putInt("daycount", count)
        editor?.apply()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}