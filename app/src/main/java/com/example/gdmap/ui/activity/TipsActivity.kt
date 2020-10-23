package com.example.gdmap.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.bean.Tip
import com.example.gdmap.ui.adapter.QuestionItemAdapter
import com.example.gdmap.ui.adapter.TipsAdapter
import com.example.gdmap.ui.viewmodel.CollectViewModel
import com.example.gdmap.ui.viewmodel.TipsViewModel
import com.example.gdmap.utils.LogUtils
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_collect.*
import kotlinx.android.synthetic.main.activity_tips.*
import kotlinx.android.synthetic.main.activity_tips.recyclerView
import kotlinx.android.synthetic.main.activity_tips.toolbar

/**
 * @Author: xgl
 * @ClassName: TIpsActivity
 * @Description:
 * @Date: 2020/10/4 20:55
 */
class TipsActivity :BaseActivity(){
    private var tipsAdapter: TipsAdapter? = null
    private val tabItemList= arrayListOf<String>("火灾","地震","水灾","飓风（龙卷风、台风等）")
    private val viewModel by lazy {  ViewModelProviders.of(this).get(TipsViewModel::class.java)}
    private var tittle="火灾"
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTips(tabItemList[0])

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        tipsAdapter = this.let { TipsAdapter(it) }
        recyclerView.adapter=tipsAdapter

        viewModel.tipData.observe(this, Observer {
            tipsAdapter?.addData(it,tittle)
            LogUtils.log_d<String>(it.toString())
        })

    }

    override fun initView() {
        setSupportActionBar(toolbar)

    }

    override fun initClick() {
    }

    override fun initData() {
        tl_category.setTitle(tabItemList)
        tl_category.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.customView != null) {
                    val tab_layout_text: TextView = tab.customView!!.findViewById(R.id.map_tv_label_item)
                    tittle=tab_layout_text.text.toString()
                    if (tittle.equals("飓风（龙卷风、台风等）")){
                        tittle="飓风"
                        viewModel.getTips(tittle)
                    }else{
                        viewModel.getTips(tittle)
                    }

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_tips
    }
}