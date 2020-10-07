package com.example.gdmap.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity

/**
 * @Author: xgl
 * @ClassName: ProductActivity
 * @Description:
 * @Date: 2020/10/5 21:36
 */
class ProductActivity :BaseActivity()
{
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun initView() {
    }

    override fun initClick() {

    }


    override fun initData() {
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_product
    }

}