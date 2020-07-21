package com.example.gdmap.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gdmap.R
import com.example.gdmap.database.ArticleDataBase
import com.example.gdmap.ui.GetArticleData
import com.example.gdmap.ui.adapter.ArticleAdapter
import com.example.gdmap.ui.viewmodel.ArticleViewModel
import com.example.gdmap.utils.RecycleViewScrollListener
import com.example.gdmap.utils.ToastUtils.showToast
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment() {
    private var articleAdapter: ArticleAdapter? = null
    private val LOAD_MORE=0
    private val LOAD=1
    var articleList = ArrayList<com.example.gdmap.database.ArticleDataBase.Data>()
    val articleViewModel by lazy { ViewModelProviders.of(this).get(ArticleViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        rv_fragment_article_item.layoutManager = layoutManager
        articleAdapter = context?.let { ArticleAdapter(it) }
        rv_fragment_article_item.adapter = articleAdapter
        rv_fragment_article_item.addOnScrollListener(object : RecycleViewScrollListener() {

            override fun loadMore() {
                "加载更多".showToast()
                init(LOAD_MORE)
            }
        })
        //设置刷新球颜色
        sw_fragment_article_.setColorSchemeColors(
            Color.BLUE,
            Color.RED,
            Color.YELLOW
        )
        sw_fragment_article_.setProgressBackgroundColorSchemeColor(Color.parseColor("#BBFFFF"))
        sw_fragment_article_.isRefreshing = true
        init(LOAD)
        sw_fragment_article_.isRefreshing = false
        sw_fragment_article_.setOnRefreshListener {
            init(LOAD_MORE)
            sw_fragment_article_.isRefreshing = false
        }
    }

    private fun init(msg:Int) {
        articleList.clear()
        articleViewModel.getArticleData(object : GetArticleData {
            override fun success(data: List<ArticleDataBase.Data>) {
                articleList = data as ArrayList<ArticleDataBase.Data>
                if(msg==1)
                sendMsg(0)
                else
                    sendMsg(1)
            }

            override fun failure() {

            }
        })
    }

    fun sendMsg(index: Int) {
        val message = Message()
        message.what = index
        handler.sendMessage(message)
    }

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0)
                articleAdapter?.refreshDataList(articleList)
            if (msg.what == 1)
                articleAdapter?.addDataList(articleList)
        }
    }
}