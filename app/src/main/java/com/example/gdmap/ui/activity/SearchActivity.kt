package com.example.gdmap.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity

/**
 * @Author: 徐国林
 * @ClassName: SearchActivity
 * @Description:
 * @Date: 2020/9/12 13:57
 */
class SearchActivity :BaseActivity(){
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    override fun getViewLayout(): Int {
       return R.layout.activity_search
    }

    override fun initView() {

    }

    override fun initClick() {

    }

    override fun initData() {

    }
}