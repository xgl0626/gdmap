package com.example.gdmap.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.database.PlayDataBase
import com.example.gdmap.ui.GetPlayData
import com.example.gdmap.ui.adapter.HistoryAdapter
import com.example.gdmap.ui.viewmodel.PlayVIewModel
import com.example.gdmap.utils.ImmersedStatusbarUtils
import com.example.gdmap.utils.MyApplication.Companion.context
import kotlinx.android.synthetic.main.activity_history.*


class HistoryActivity : BaseActivity() {
    private val playVIewModel by lazy { ViewModelProviders.of(this).get(PlayVIewModel::class.java) }
    private var playItemLists = ArrayList<PlayDataBase.NewsList>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用
        ImmersedStatusbarUtils.initSetContentView(this, toolBar)
        initdata()
    }

    private fun initdata() {
        val time = android.text.format.Time()
        time.setToNow()
        val currentDay = time.monthDay.toString()
        val month = "0" + (time.month + 1).toString()
        toolBar.title = "历史上的今天"
        playVIewModel.getHistory((month + currentDay), object : GetPlayData {
            override fun success(data: PlayDataBase) {
                playItemLists.clear()
                playItemLists = data.newslist as ArrayList<PlayDataBase.NewsList>
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
            when (msg.what) {
                0 -> {
                    val layoutManager = LinearLayoutManager(context)
                    rv_activity_history.layoutManager = layoutManager
                    //添加Android自带的分割线
                    rv_activity_history.addItemDecoration(
                        DividerItemDecoration(
                            context,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    val historyAdapter = context?.let { HistoryAdapter(it, playItemLists) }
                    rv_activity_history.adapter = historyAdapter
                    historyAdapter?.notifyDataSetChanged()
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