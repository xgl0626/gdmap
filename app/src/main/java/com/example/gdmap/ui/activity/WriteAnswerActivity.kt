package com.example.gdmap.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import kotlinx.android.synthetic.main.activity_answer.*

/**
 * @Author: xgl
 * @ClassName: WriteAnswerActivity
 * @Description:
 * @Date: 2020/10/4 17:27
 */
class WriteAnswerActivity : BaseActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setSupportActionBar(toolbar)
    }

    override fun initClick() {
    }

    override fun initData() {
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_answer
    }

}