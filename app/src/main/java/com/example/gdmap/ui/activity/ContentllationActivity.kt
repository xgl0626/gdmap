package com.example.gdmap.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.database.PlayDataBase
import com.example.gdmap.ui.GetPlayData
import com.example.gdmap.ui.viewmodel.PlayVIewModel
import com.example.gdmap.utils.ImmersedStatusbarUtils
import com.example.gdmap.utils.LogUtils
import kotlinx.android.synthetic.main.acticity_constellation.*
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_history.toolBar

class ContentllationActivity :BaseActivity()
{
    private val playVIewModel by lazy { ViewModelProviders.of(this).get(PlayVIewModel::class.java) }
    private var playItemLists=ArrayList<PlayDataBase.NewsList>()
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acticity_constellation)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用
        ImmersedStatusbarUtils.initSetContentView(this,toolBar)
        toolBar.title = "星座配对"
        iv_activity_constellation_search.setOnClickListener {
            initData()
        }
    }

    private fun initData() {
        playVIewModel.getConstellation(et_activity_constellation.text.toString(),
            object :GetPlayData{
                override fun success(data: PlayDataBase) {
                    LogUtils.log_d<String>(data.toString())
                    playItemLists= data.newslist as ArrayList<PlayDataBase.NewsList>
                    sendMsg(0)
                }

                override fun failure() {
                }

            })
    }
    private val handler: Handler = object : Handler() {
        @SuppressLint("HandlerLeak")
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what)
            {
                0->{
                    tv_activity_constellation_content.text=playItemLists[0].content
                    tv_activity_constellation_title.text =playItemLists[0].grade
                }
            }
        }
    }
    fun sendMsg(index: Int) {
        val message = Message()
        message.what = index
        handler.sendMessage(message)
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