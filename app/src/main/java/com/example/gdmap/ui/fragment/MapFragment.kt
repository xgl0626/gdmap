package com.example.gdmap.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.LocationSource
import com.amap.api.maps.LocationSource.OnLocationChangedListener
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.maps.model.animation.RotateAnimation
import com.amap.api.maps.offlinemap.OfflineMapActivity
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.example.gdmap.R
import com.example.gdmap.ui.activity.TimeBusActivity
import com.example.gdmap.ui.activity.WalkActivity
import com.example.gdmap.ui.activity.WeatherActivity
import com.example.gdmap.ui.adapter.ServiceAdapter
import com.example.gdmap.utils.GudieUtils
import com.example.gdmap.utils.ImmersedStatusbarUtils
import com.example.gdmap.utils.InputTipUtils
import com.example.gdmap.utils.LogUtils
import com.example.gdmap.utils.ToastUtils.showToast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import kotlinx.android.synthetic.main.activity_bottom_sheet_search.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map_bottom_sheet.*
import java.text.SimpleDateFormat
import java.util.*


class MapFragment : Fragment(), LocationSource, AMapLocationListener, TextWatcher,
    AMap.OnMapTouchListener,PoiSearch.OnPoiSearchListener {
    private var aMap: AMap? = null
    private var isFirst: Boolean = true
    private var mListener: OnLocationChangedListener? = null
    private var mLocationClient: AMapLocationClient? = null
    private var cityString: String? = null
    private var contentPlace: String? = null
    private var surlng: Double? = null
    private var surrlat: Double? = null
    private var lat: Double? = null
    private var lng: Double? = null
    private var poiList = ArrayList<PoiItem>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_map, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.activity?.let { ImmersedStatusbarUtils.initSetContentView(it,null) }
        mv_map.onCreate(savedInstanceState)
        aMap = mv_map.map
        val mUiSettings: UiSettings? = aMap?.uiSettings
        mUiSettings?.isCompassEnabled = true
        aMap?.let { initMap(it) }
        initView()
        initImageView()
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, end: Int, after: Int) {
        if (s?.length!! < 0) {
            return
        } else {
            cityString?.let {
                InputTipUtils.setContext(et_search, context).initInputTip(
                    s.toString().trim(),
                    it
                )
            }
        }
    }


    private fun initView() {
        val behavior1 = BottomSheetBehavior.from(bottom_sheet1)
        val behavior2 = BottomSheetBehavior.from(ns_search_dialog)
        behavior1.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> behavior1.state = STATE_COLLAPSED
                }
            }

        })
        behavior2.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {

            }

            override fun onStateChanged(p0: View, p1: Int) {
                when (p1) {
                    STATE_COLLAPSED -> behavior2.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }

        })

        iv_fragment_food_back.setOnClickListener { view ->
            behavior2.state = STATE_HIDDEN

        }
        bs_rb_food.setOnClickListener { view ->
            poiList.clear()
            ns_search_dialog.visibility = View.VISIBLE
            behavior2.state = BottomSheetBehavior.STATE_EXPANDED
            context?.let {
                surrlat?.let { it1 ->
                    surlng?.let { it2 ->
                        cityString?.let { it3 ->
                            searchPlace(
                                it, "050000",
                                it1, it2, it3
                            )
                        }
                    }
                }
            }
        }
        bs_rb_travel.setOnClickListener { view ->
            poiList.clear()
            tv_fragment_service_title.text="景点"
            ns_search_dialog.visibility = View.VISIBLE
            behavior2.state = BottomSheetBehavior.STATE_EXPANDED
            context?.let {
                surrlat?.let { it1 ->
                    surlng?.let { it2 ->
                        cityString?.let { it3 ->
                            searchPlace(
                                it, "110000",
                                it1, it2, it3
                            )
                        }
                    }
                }
            }
        }
        bs_rb_home.setOnClickListener { view ->
            poiList.clear()
            tv_fragment_service_title.text="酒店"
            ns_search_dialog.visibility = View.VISIBLE
            behavior2.state = BottomSheetBehavior.STATE_EXPANDED
            context?.let {
                surrlat?.let { it1 ->
                    surlng?.let { it2 ->
                        cityString?.let { it3 ->
                            searchPlace(
                                it, "100000",
                                it1, it2, it3
                            )
                        }
                    }
                }
            }
        }
        bs_rb_ilo.setOnClickListener { view ->
            poiList.clear()
            tv_fragment_service_title.text="加油站"
            ns_search_dialog.visibility = View.VISIBLE
            behavior2.state = BottomSheetBehavior.STATE_EXPANDED
            context?.let {
                surrlat?.let { it1 ->
                    surlng?.let { it2 ->
                        cityString?.let { it3 ->
                            searchPlace(
                                it, "010000", 
                                it1, it2, it3
                            )
                        }
                    }
                }
            }
        }

        bs_rb_time_bus.setOnClickListener {
            val intent = Intent(this.context, TimeBusActivity::class.java)
            intent.putExtra("city",cityString)
            startActivity(intent)
        }
        bs_rb_walk.setOnClickListener {
            val intent = Intent(this.context, WalkActivity::class.java)
            intent.putExtra("lat",surrlat)
            intent.putExtra("lng",surlng)
            intent.putExtra("city",cityString)
            startActivity(intent)
        }
        bs_rb_more2.setOnClickListener {
            "目前未开放此功能".showToast()
        }
        et_search.addTextChangedListener(this)
        et_search.onItemClickListener =
            AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                lat = InputTipUtils.getTipList()?.get(p2)?.point?.latitude
                lng = InputTipUtils.getTipList()?.get(p2)?.point?.longitude
                LogUtils.log_d<Drawable>(lat.toString() + lng.toString())
                lng?.let { lat?.let { it1 -> drawMarker(it1, it) } }
                aMap?.moveCamera(
                    CameraUpdateFactory.changeLatLng(
                        lat?.let {
                            lng?.let { it1 ->
                                LatLng(
                                    it, it1
                                )
                            }
                        }
                    )
                )
                behavior2.state = BottomSheetBehavior.STATE_HIDDEN
            }
        bs_rb_drive_car.setOnClickListener {view->
                context?.let { GudieUtils.startGuilde(it) }
        }
        bs_rb_leave_map.setOnClickListener{
            view ->
            startActivity(
                Intent(
                    this.context,
                    OfflineMapActivity::class.java
                )
            )
        }
        fab_fragment_map_weather.setOnClickListener { view ->
//            cityString?.let { context?.let { it1 -> WeatherSearchUtils.weatherSearch(it, it1) } }
                val intent=Intent(this.activity,WeatherActivity::class.java)
                intent.putExtra("cityname",cityString)
                startActivity(intent)
        }

    }

    //手动调整button中的图片
    private fun initImageView() {
        setImageViewAndButton(R.drawable.bt_rb_timebus, bs_rb_time_bus, 1)
        setImageViewAndButton(R.drawable.bt_rb_car, bs_rb_drive_car, 1)
        setImageViewAndButton(R.drawable.bt_rb_gobackwork, bs_rb_walk, 1)
        setImageViewAndButton(R.drawable.bt_rb_loadmap, bs_rb_leave_map, 1)
        setImageViewAndButton(R.drawable.bt_rb_collect, bs_rb_more2, 1)
        setImageViewAndButton(R.mipmap.fragment_bs_search, et_search, 2)
        setImageViewAndButton(R.drawable.bt_rb_trip, bs_rb_travel, 1)
        setImageViewAndButton(R.drawable.bt_search_food, bs_rb_food, 1)
        setImageViewAndButton(R.drawable.bt_search_home, bs_rb_home, 1)
        setImageViewAndButton(R.drawable.bt_search_more2, bs_rb_more2, 1)
        setImageViewAndButton(R.drawable.bt_search_oil, bs_rb_ilo, 1)
    }


    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this.context, activity::class.java)
        startActivity(intent)
    }
    /**
     * 画定位图
     * @param lat
     * @param lng
     */
    private fun drawMarker(lat: Double, lng: Double) {
        aMap?.clear(true)
        val markerOptions = MarkerOptions()
            .position(LatLng(lat, lng))
            .draggable(true)
            .setFlat(true)
        val marker = aMap!!.addMarker(markerOptions)
        val animation: RotateAnimation =
            RotateAnimation(marker.getRotateAngle(), marker.getRotateAngle() + 180F, 0F, 0F, 0F)
        val duration = 1000L
        animation.setDuration(duration)
        animation.setInterpolator(LinearInterpolator())
        marker.setAnimation(animation)
        marker.startAnimation()
        marker.showInfoWindow()
    }

    private fun initMap(aMap: AMap) {
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)
//设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效,单位为毫秒。
        myLocationStyle.interval(2000)
//设置定位蓝点的Style
        aMap.myLocationStyle = myLocationStyle
//设置默认定位按钮是否显示，非必需设置。
        aMap.uiSettings.isMyLocationButtonEnabled = true
// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.isMyLocationEnabled = true
        location()
    }

    private fun location() {
        mLocationClient = AMapLocationClient(context)
        mLocationClient!!.setLocationListener(this)
        val mLocationOption = AMapLocationClientOption()
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.isNeedAddress = true
        //设置是否只定位一次,默认为false
        mLocationOption.isOnceLocation = false
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.isWifiActiveScan = true
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.isMockEnable = true
        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.interval =2000
        //给定位客户端对象设置定位参数
        mLocationClient!!.setLocationOption(mLocationOption)
        //启动定位
        mLocationClient!!.startLocation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mv_map.onDestroy()
        mLocationClient!!.stopLocation()
        mLocationClient!!.onDestroy()

    }

    override fun onResume() {
        super.onResume()
        mv_map.onResume()
    }

    override fun onPause() {
        super.onPause()
        mv_map.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mv_map.onSaveInstanceState(outState)
    }


    private fun setImageViewAndButton(drawable: Int, view: RadioButton, id: Int) {
        val drawable: Drawable =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                resources.getDrawable(drawable, null)
            } else {
                TODO("VERSION.SDK_INT < LOLLIPOP")
            }
        drawable.setBounds(0, 0, 80, 80)
        when (id) {
            1 -> view.setCompoundDrawables(null, drawable, null, null)
            2 -> view.setCompoundDrawables(null, null, drawable, null)
        }
    }


    private fun setImageViewAndButton(drawable: Int, view: TextView, id: Int) {
        val drawable: Drawable =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                resources.getDrawable(drawable, null)
            } else {
                TODO("VERSION.SDK_INT < LOLLIPOP")
            }
        drawable.setBounds(0, 0, 80, 80)
        when (id) {
            1 -> view.setCompoundDrawables(null, drawable, null, null)
            2 -> view.setCompoundDrawables(null, null, drawable, null)
        }
    }

    override fun deactivate() {
        mListener = null
    }

    override fun activate(p0: LocationSource.OnLocationChangedListener?) {
        mListener = p0
    }

    override fun onLocationChanged(aMapLocation: AMapLocation?) {
        if (aMapLocation != null) {
            if (aMapLocation.errorCode == 0) {
                aMapLocation.locationType//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                aMapLocation.latitude//获取纬度
                aMapLocation.longitude//获取经度
                surrlat = aMapLocation.latitude
                surlng = aMapLocation.longitude
                aMapLocation.accuracy//获取精度信息
                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                val date = Date(aMapLocation.getTime())
                df.format(date)//定位时间
                aMapLocation.address//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.country//国家信息
                aMapLocation.province//省信息
                aMapLocation.city//城市信息
                cityString = aMapLocation.city
                aMapLocation.district//城区信息
                aMapLocation.street//街道信息
                aMapLocation.streetNum//街道门牌号信息
                aMapLocation.cityCode//城市编码
                aMapLocation.adCode//地区编码

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirst) {
                    //设置缩放级别
                    aMap?.moveCamera(CameraUpdateFactory.zoomTo(17F))
                    //将地图移动到定位点
                    aMap?.moveCamera(
                        CameraUpdateFactory.changeLatLng(
                            LatLng(
                                aMapLocation.latitude,
                                aMapLocation.longitude
                            )
                        )
                    )
                    drawMarker(aMapLocation.latitude, aMapLocation.longitude)
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener?.onLocationChanged(aMapLocation)
                    //添加图钉
                    //  aMap.addMarker(getMarkerOptions(amapLocation))
                    //获取定位信息
                    val buffer = StringBuffer()
                    buffer.append(
                        aMapLocation.country + ""
                                + aMapLocation.province + ""
                                + aMapLocation.city + ""
                                + aMapLocation.province + ""
                                + aMapLocation.district + ""
                                + aMapLocation.street + ""
//                                + aMapLocation.streetNum
                    );
                    buffer.toString().showToast()
                    contentPlace = buffer.toString()
                    isFirst = false
                }
            }
//            else{
//                LogUtils.log_d<String>( "location Error, ErrCode:"
//                        + aMapLocation.errorCode + ", errInfo:"
//                        + aMapLocation.errorInfo
//                )
//                 "定位失败".showToast()
//            }
        }
    }

    override fun onTouch(p0: MotionEvent?) {
        if (isFirst)
            isFirst = false
    }

    fun searchPlace(
        context: Context,
        serviceAparts: String,
        lat: Double,
        lng: Double,
        city: String
    ) {
        val query = PoiSearch.Query("", serviceAparts, city)
        query.pageSize = 50
        query.pageNum
        val poiSearch = PoiSearch(context, query)
        poiSearch.bound = PoiSearch.SearchBound(
            LatLonPoint(
                lat, lng
            ), 5000
        ) //设置周边搜索的中心点以及半径
        poiSearch.setOnPoiSearchListener(this)
        poiSearch.searchPOIAsyn()
    }

    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {

    }

    override fun onPoiSearched(result: PoiResult?, code: Int) {
        if (code == 1000) {
            for (poiItem in result?.pois!!) {
                poiList.add(poiItem)
                drawMarker(poiItem.latLonPoint.latitude,poiItem.latLonPoint.longitude)
            }
            sendMsg(0)
        } else {
            "未查询到结果".showToast()
        }
    }

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what==0){
                val layoutManager = LinearLayoutManager(context)
                rv_fragment_food_down.layoutManager = layoutManager
                val serviceAdapter =  ServiceAdapter(surrlat,surlng,context,poiList)
                rv_fragment_food_down.adapter = serviceAdapter
                serviceAdapter?.notifyDataSetChanged()
                for (poitem in poiList){
                    val markerOption = MarkerOptions();
                    markerOption.position(LatLng(poitem.latLonPoint.latitude,
                        poitem.latLonPoint.longitude))
                        .draggable(false).title(poitem.title.toString()).setFlat(true).visible(true).infoWindowEnable(true)
                    aMap?.addMarker(markerOption);
                }
            }
        }
    }
    fun sendMsg(index: Int) {
        val message = Message()
        message.what = index
        handler.sendMessage(message)
    }
}

