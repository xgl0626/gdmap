package com.example.gdmap.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.database.RobotDataBase
import com.example.gdmap.ui.GetRobotData
import com.example.gdmap.ui.adapter.RobotAdapter
import com.example.gdmap.ui.viewmodel.RobotViewModel
import com.example.gdmap.utils.ImmersedStatusbarUtils
import com.example.gdmap.utils.MyApplication.Companion.context
import kotlinx.android.synthetic.main.activity_article_content.toolBar
import kotlinx.android.synthetic.main.activity_bottom_sheet_search.*
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_weather.*
import kotlin.random.Random.Default.Companion

class QuestionActivity :BaseActivity()
{
    private var robotDataList=ArrayList<com.example.gdmap.ui.activity.Message>()
  private val robotViewModel by lazy { ViewModelProviders.of(this).get(RobotViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用
        ImmersedStatusbarUtils.initSetContentView(this,toolBar)
        initView()

    }

    override fun onStart() {
        super.onStart()
}

    fun initView() {
        tv_activity_question_send_msg.setOnClickListener {
            robotDataList.add(com.example.gdmap.ui.activity.Message(et_msg.text.toString(),0))
            robotViewModel.getRobotData(et_msg.text.toString(), object : GetRobotData {
                override fun success(data: RobotDataBase) {
                    robotDataList.add(com.example.gdmap.ui.activity.Message(data.newslist[0].reply,1))
                    sendMsg(0)
                }

                override fun failure() {
                }
            })
            et_msg.setText("")
        }
    }
    private val handler: Handler = object : Handler() {
    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        if(msg.what==0){
            val layoutManager = LinearLayoutManager(context)
           recycler_question.layoutManager = layoutManager
            val robotAdapter = RobotAdapter(context,robotDataList)
            recycler_question.adapter = robotAdapter
            robotAdapter.notifyDataSetChanged()
        }
    }
}
    fun sendMsg(index: Int) {
        val message = Message()
        message.what = index
        handler.sendMessage(message)
    }


    override fun onDestroy() {
        super.onDestroy()
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