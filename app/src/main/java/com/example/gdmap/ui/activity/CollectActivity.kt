package com.example.gdmap.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import androidx.annotation.RequiresApi
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import kotlinx.android.synthetic.main.activity_collect.*

class CollectActivity() : BaseActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
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
    }

    override fun initClick() {
    }

    override fun initData() {
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_collect
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}