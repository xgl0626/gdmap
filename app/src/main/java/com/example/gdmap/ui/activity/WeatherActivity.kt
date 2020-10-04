package com.example.gdmap.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.amap.api.services.weather.LocalWeatherForecastResult
import com.amap.api.services.weather.LocalWeatherLiveResult
import com.amap.api.services.weather.WeatherSearch
import com.amap.api.services.weather.WeatherSearchQuery
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.utils.ImmersedStatusbarUtils
import com.example.gdmap.utils.LogUtils
import com.example.gdmap.utils.MyApplication.Companion.context
import com.example.gdmap.utils.Toast
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity :BaseActivity(),WeatherSearch.OnWeatherSearchListener
{
    private var city:String?=null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true) //设置返回键可用
        val intent = intent
        city = intent.getStringExtra("cityname")
        LogUtils.log_d<String>(city.toString())
        city?.let { weatherNowTimeSearch(it) }
        city?.let { weatherPredictSearch(it) }
    }

    override fun initView() {
    }

    override fun initClick() {

    }

    override fun initData() {

    }

    override fun getViewLayout(): Int {
       return R.layout.activity_weather
    }

    fun weatherNowTimeSearch(city:String)
    {
        val mquery= WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE)
        val mweathersearch= WeatherSearch(context)
        mweathersearch.setOnWeatherSearchListener(this)
        mweathersearch.query = mquery
        mweathersearch.searchWeatherAsyn()
    }
    fun weatherPredictSearch(city:String)
    {
        val mquery= WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_FORECAST)
        val mweathersearch= WeatherSearch(context)
        mweathersearch.setOnWeatherSearchListener(this)
        mweathersearch.query = mquery
        mweathersearch.searchWeatherAsyn()
    }
    override fun onWeatherLiveSearched(localWeatherLiveResult: LocalWeatherLiveResult?, code: Int) {
        if(code==1000)
        {
            if(localWeatherLiveResult!=null&&localWeatherLiveResult.liveResult!=null)
            {
                val weatherlive=localWeatherLiveResult.liveResult
                tv_activity_weather_city_name.text=city
                tv_activity_weather_nowtime.text=weatherlive.reportTime+" 发布"
                tv_activity_weather_tmp.text=weatherlive.temperature+" 度"
                tv_activity_weather.text=weatherlive.weather
                tv_activity_weather_wind.text=weatherlive.windDirection+"风"
            }else{
                Toast.toast("没有查询到天气情况")
            }
        }
    }

    override fun onWeatherForecastSearched(localWeatherPredictResult: LocalWeatherForecastResult?, code: Int) {
        if(code==1000)
        {
            if(localWeatherPredictResult!=null&&localWeatherPredictResult.forecastResult!=null)
            {
                val weatherPrediction=localWeatherPredictResult.forecastResult
                tv_activity_weather_predict.text=weatherPrediction.reportTime+" 发布"
                tv_activity_weather_predicttime1.text=weatherPrediction.weatherForecast[0].date
                tv_activity_weather_predicttmp.text=weatherPrediction.weatherForecast[0].dayTemp+" 度"
                tv_activity_weather_predicttime2.text=weatherPrediction.weatherForecast[1].date
                tv_activity_weather_predicttmp2.text=weatherPrediction.weatherForecast[1].dayTemp+" 度"
                tv_activity_weather_predicttime3.text=weatherPrediction.weatherForecast[2].date
                tv_activity_weather_predicttmp3.text=weatherPrediction.weatherForecast[2].dayTemp+" 度"
            }else{
                Toast.toast("没有查询到天气情况")
            }
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