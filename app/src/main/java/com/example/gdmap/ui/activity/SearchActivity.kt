package com.example.gdmap.ui.activity

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.ui.adapter.QuestionItemAdapter
import com.example.gdmap.ui.viewmodel.SearchViewModel
import com.example.gdmap.utils.LogUtils
import com.example.gdmap.utils.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_search.*

/**
 * @Author: 徐国林
 * @ClassName: SearchActivity
 * @Description:
 * @Date: 2020/9/12 13:57
 */
class SearchActivity : BaseActivity() {
    private var dialog: ProgressDialog? = null
    private var messageItemAdapter: QuestionItemAdapter? = null
    private val viewModel by lazy { ViewModelProviders.of(this).get(SearchViewModel::class.java) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        messageItemAdapter = QuestionItemAdapter(this)
        recyclerView.adapter = messageItemAdapter
    }

    override fun initClick() {
        iv_search.setOnSingleClickListener {
            val searchData = et_search.text.toString()
            viewModel.search(searchData)
        }
    }

    override fun initData() {
        viewModel.questionList.observe(this, Observer {
            messageItemAdapter?.addData(it)
            LogUtils.log_d<String>(it.toString())
        })
        dialog?.dismiss()
    }
}