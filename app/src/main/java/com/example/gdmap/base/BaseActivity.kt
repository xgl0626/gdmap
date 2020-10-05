package com.example.gdmap.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getViewLayout())
        if (supportActionBar != null)
            supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用
        initFlag()
        initView()
        initData()
        initClick()
    }

    abstract fun initView()
    abstract fun initClick()
    abstract fun initData()
    abstract fun getViewLayout(): Int
    private fun initFlag() {

        when {
            Build.VERSION.SDK_INT >= 23 -> {
                window.decorView.systemUiVisibility =
                        //亮色模式状态栏
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                            //设置decorView的布局设置为全屏
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            //维持布局稳定，不会因为statusBar和虚拟按键的消失而移动view位置
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
            Build.VERSION.SDK_INT >= 21 -> {
                //设置decorView的布局设置为全屏，并维持布局稳定
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.statusBarColor = Color.TRANSPARENT
            }
        }
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