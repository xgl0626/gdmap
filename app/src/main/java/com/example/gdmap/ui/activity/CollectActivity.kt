package com.example.gdmap.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.ui.adapter.QuestionItemAdapter
import com.example.gdmap.ui.viewmodel.CollectViewModel
import com.example.gdmap.utils.LogUtils
import kotlinx.android.synthetic.main.activity_collect.*

class CollectActivity() : BaseActivity() {
    private var collectItemAdapter: QuestionItemAdapter? = null
    private val viewModel by lazy { ViewModelProviders.of(this).get(CollectViewModel::class.java) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCollectQuestionList()
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        collectItemAdapter = this.let { QuestionItemAdapter(it) }
        recyclerView.adapter = collectItemAdapter
        srl_collect.setOnRefreshListener {
            viewModel.getCollectQuestionList()
            srl_collect.isRefreshing = false
        }
        viewModel.collectData.observe(this, Observer {
            collectItemAdapter?.addData(it)
            LogUtils.log_d<String>(it.toString())
        })
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return false
        }
        return false
    }

    @SuppressLint("ResourceAsColor")
    override fun initView() {
        setSupportActionBar(toolbar)
    }

    override fun initClick() {
    }

    override fun initData() {
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_collect
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}