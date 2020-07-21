package com.example.gdmap.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.services.busline.*
import com.amap.api.services.busline.BusLineQuery.SearchType
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.ui.adapter.ArticleAdapter
import com.example.gdmap.ui.adapter.BusStationAdapter
import com.example.gdmap.utils.ImmersedStatusbarUtils
import com.example.gdmap.utils.LogUtils
import com.example.gdmap.utils.MyApplication.Companion.context
import com.example.gdmap.utils.ToastUtils.showToast
import kotlinx.android.synthetic.main.activity_article_content.toolBar
import kotlinx.android.synthetic.main.activity_timebus.*
import kotlinx.android.synthetic.main.fragment_article.*

class TimeBusActivity :BaseActivity(),BusStationSearch.OnBusStationSearchListener,BusLineSearch.OnBusLineSearchListener{
    private var city:String?=null
    private var busStationAdapter:BusStationAdapter?=null
    private var busStationList=ArrayList<BusStationItem>()
    private var busLineList=ArrayList<BusLineItem>()
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timebus)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用
        ImmersedStatusbarUtils.initSetContentView(this,toolBar)
        val intent=intent
        city=intent.getStringExtra("city")
        city?.showToast()
        val layoutManager = LinearLayoutManager(context)
        rv_bus_load.layoutManager = layoutManager
        busStationAdapter= BusStationAdapter(context)
        rv_bus_load.adapter=busStationAdapter
        iv_activity_bus_search_road.setOnClickListener {
            val place = et_activity_bus.text.toString()
                city?.let { it1 -> initStationData(place, it1) }
                busStationAdapter!!.addDataList(busStationList, busLineList)
        }

    }

    private fun initLinesData(place:String,city:String) {
        val busLineQuery = BusLineQuery(place, SearchType.BY_LINE_NAME, city)
        busLineQuery.pageSize = 10
        busLineQuery.pageNumber =1
        val busLineSearch = BusLineSearch(this, busLineQuery)
        busLineSearch.setOnBusLineSearchListener(this)
        busLineSearch.searchBusLineAsyn()
    }

    private fun initStationData(place: String,city: String) {
        val busStationQuery=BusStationQuery(place,city)
        val busStationSearch=BusStationSearch(this,busStationQuery)
        busStationSearch.setOnBusStationSearchListener(this)
        busStationSearch.searchBusStationAsyn()

    }

    override fun onBusStationSearched(result: BusStationResult?, code: Int) {
        if(code==1000)
        {
            if (result != null) {
                busStationList.clear()
                busStationList.addAll(result.busStations)
            }
        }
        else{
            "未查询到公交站点信息".showToast()
        }
    }

    override fun onBusLineSearched(result: BusLineResult?, code: Int) {
            if(code==1000)
            {
                if (result != null) {
                    LogUtils.log_d<String>(result.toString())
                    busLineList.addAll(result.busLines)
                }
            }
        else{
                "未查询到公交线路".showToast()
            }
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