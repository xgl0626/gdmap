package com.example.gdmap.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.gdmap.R
import kotlinx.android.synthetic.main.activity_article.*

/**
 * @Author: 徐国林
 * @ClassName: ArticleActivity
 * @Description:
 * @Date: 2020/9/5 21:56
 */
class ArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        initView()
        initData()
    }

    private fun initData() {

    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}