package com.example.gdmap.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.database.PlayDataBase
import com.example.gdmap.database.RobotDataBase
import com.example.gdmap.ui.GetPlayData
import com.example.gdmap.ui.adapter.RobotAdapter
import com.example.gdmap.ui.viewmodel.PlayVIewModel
import com.example.gdmap.utils.ImmersedStatusbarUtils
import com.example.gdmap.utils.LogUtils
import com.example.gdmap.utils.MyApplication
import kotlinx.android.synthetic.main.activity_article_content.*
import kotlinx.android.synthetic.main.activity_comments_or_joke.*
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_question.toolBar

class PlayItemActivity :BaseActivity()
{
    private val playVIewModel by lazy { ViewModelProviders.of(this).get(PlayVIewModel::class.java) }
    private var playItemLists=ArrayList<PlayDataBase.NewsList>()
    private var point:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments_or_joke)
        setSupportActionBar(tb_activity_play_joke)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用
        ImmersedStatusbarUtils.initSetContentView(this,tb_activity_play_joke)
        val intent=intent
        point=intent.getStringExtra("point")
        initData(point)
        bt_activity_play_commtents_next.setOnClickListener {
            initData(point)
        }
    }

    private fun initData(point: String?) {
        when(point)
        {
            "0"->{
                tb_activity_play_joke.title = "每日一笑"
                playVIewModel.getJoke(object :GetPlayData{
                    override fun success(data: PlayDataBase) {
                        playItemLists.clear()
                       playItemLists= data.newslist as ArrayList<PlayDataBase.NewsList>
                        sendMsg(0)
                    }

                    override fun failure() {
                    }
                })
            }
            "1"->{
                tb_activity_play_joke.title ="网易云热评"
                playVIewModel.getComments(object :GetPlayData{
                    override fun success(data: PlayDataBase) {
                        playItemLists.clear()
                        playItemLists= data.newslist as ArrayList<PlayDataBase.NewsList>
                        sendMsg(1)
                    }

                    override fun failure() {
                    }
                })
            }
        }
    }
    private val handler: Handler = object : Handler() {
        @SuppressLint("HandlerLeak")
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what)
            {
                0->{
                    tv_activity_play_comments_title.text = playItemLists[0].title
                    tv_activity_play_comments_content.text=playItemLists[0].content
                }
                1->{
                    tv_activity_play_comments_title.text=playItemLists[0].source
                    tv_activity_play_comments_content.text=playItemLists[0].content
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