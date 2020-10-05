package com.example.gdmap.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.utils.AddIconImage
import kotlinx.android.synthetic.main.activity_search.*

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
    }

    override fun getViewLayout(): Int {
       return R.layout.activity_search
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        AddIconImage.setImageViewToEditText(R.drawable.ic_search, et_search, 1)

    }

    override fun initClick() {

    }

    override fun initData() {

    }
}