package com.example.gdmap.ui.activity

import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.utils.ImmersedStatusbarUtils
import kotlinx.android.synthetic.main.activity_article_content.*
import kotlinx.android.synthetic.main.activity_login.*

class ArticleContentActivity:BaseActivity() {
    var url: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_content)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用
        ImmersedStatusbarUtils.initSetContentView(this, toolBar)
        val intent = intent
        url = intent.getStringExtra("url")
        val webSettings: WebSettings = wv_article_content.settings
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //设置缓存

        webSettings.javaScriptEnabled = true //设置能够解析Javascript

        webSettings.domStorageEnabled = true //设置适应Html5 //重点是这个设置

        wv_article_content.setWebViewClient(WebViewClient())
        url?.let { wv_article_content.loadUrl(it) }
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