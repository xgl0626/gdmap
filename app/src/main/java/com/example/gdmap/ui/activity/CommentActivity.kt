package com.example.gdmap.ui.activity

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.ui.adapter.CommentContentAdapter
import com.example.gdmap.ui.viewmodel.ServiceViewModel
import com.example.gdmap.utils.MyApplication.Companion.context
import kotlinx.android.synthetic.main.activity_comment.*

/**
 * @Author: 徐国林
 * @ClassName: ArticleActivity
 * @Description:
 * @Date: 2020/9/5 21:56
 */
class CommentActivity : BaseActivity() {
    private var messageItemAdapter: CommentContentAdapter? = null
    private val viewModel by lazy { ViewModelProviders.of(this).get(ServiceViewModel::class.java) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_comment
    }

    override fun initData() {
        srl_main.isRefreshing=true
        srl_main.setOnRefreshListener {
            viewModel.getAnswerData()
        }
        viewModel.getHomeData()
        viewModel.getAnswerData()
        viewModel.commentData.observe(this, Observer {
            messageItemAdapter?.addCommentItem(it[0])
        })
        viewModel.messageData.observe(this, Observer {
            messageItemAdapter?.addData(it)
            srl_main.isRefreshing = false
        })
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        messageItemAdapter = CommentContentAdapter(this)
        recyclerView.adapter = messageItemAdapter
    }

    override fun initClick() {

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