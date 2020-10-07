package com.example.gdmap.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_tips.*

/**
 * @Author: xgl
 * @ClassName: TIpsActivity
 * @Description:
 * @Date: 2020/10/4 20:55
 */
class TipsActivity :BaseActivity(){

    private val tabItemList= arrayListOf<String>("火灾","地震","台风","海啸","火灾","地震","台风","海啸")
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
        tl_category.setTitle(tabItemList)
        tl_category.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_tips
    }
}