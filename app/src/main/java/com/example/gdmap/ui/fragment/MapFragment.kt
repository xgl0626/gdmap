package com.example.gdmap.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.AdapterView
import android.widget.Switch
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
import com.amap.api.maps.model.*
import com.amap.api.maps.model.animation.RotateAnimation
import com.amap.api.maps.offlinemap.OfflineMapActivity
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.amap.api.services.route.*
import com.example.gdmap.R
import com.example.gdmap.database.Choice
import com.example.gdmap.ui.activity.GudieActivity
import com.example.gdmap.ui.activity.WalkActivity
import com.example.gdmap.ui.activity.WeatherActivity
import com.example.gdmap.ui.adapter.ChoiceOutAdapter
import com.example.gdmap.utils.*
import com.example.gdmap.utils.drivingrouteutil.AMapUtil
import com.example.gdmap.utils.drivingrouteutil.DrivingRouteOverlay
import com.example.gdmap.utils.walkrouteutil.WalkRouteOverlay
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import kotlinx.android.synthetic.main.activity_bottom_sheet_search.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map_bottom_sheet.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log


class MapFragment : Fragment(), LocationSource, AMapLocationListener, TextWatcher,
    AMap.OnMapTouchListener, PoiSearch.OnPoiSearchListener, RouteChoice, GuideChoice {
    private var aMap: AMap? = null
    private var isFirst: Boolean = true
    private var mListener: OnLocationChangedListener? = null
    private var mLocationClient: AMapLocationClient? = null
    private var cityString: String? = null
    private var contentPlace: String? = null
    private var surlng= 0.0
    private var surrlat= 0.0
    private var lat: Double? = null
    private var lng: Double? = null
    private var poiList = ArrayList<PoiItem>()
    private var start_point: LatLonPoint? = null
    private var end_point: LatLonPoint? = null
    private var _city: String? = null//记录当前城市的信息
    private var route: Boolean = true
    private var endlng= 0.0
    private var endlat= 0.0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.activity?.let { ImmersedStatusbarUtils.initSetContentView(it, null) }
        mv_map.onCreate(savedInstanceState)
        aMap = mv_map.map
        val mUiSettings: UiSettings? = aMap?.uiSettings
        mUiSettings?.isCompassEnabled = true
        aMap?.let { initMap(it) }
        initView()
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

    override fun Guide(result: Int) {
        when (result) {
            //驾车导航
            0 -> {
                val start=Poi("南方花园",LatLng(surrlat,surlng),"")
                val end=Poi("丁香苑", LatLng(endlat,endlng),"")
                GudieUtils.startGuilde(MyApplication.context,start,end)
            }
            //步行导航
            1 -> {
                val intent = Intent(this.context, GudieActivity::class.java)
                intent.putExtra("lat", surrlat)
                intent.putExtra("lng", surlng)
                intent.putExtra("endlat", endlat)
                intent.putExtra("endlng", endlng)
                startActivity(intent)
            }
        }
    }

    override fun Route(result: Int) {
        when (result) {
            0 -> {
                route = true
                poiSearch()
            }
            1 -> {
                route = false
                poiSearch()
            }
        }
    }

    private fun initView() {
        val data = ArrayList<Choice>()
        data.add(Choice(R.drawable.bt_rb_car))
        data.add(Choice(R.drawable.bt_rb_gobackwork))
        val adapter = ChoiceOutAdapter(data, MyApplication.context, this)
        map_rv_choice.adapter = adapter;
        val layManager = LinearLayoutManager(MyApplication.context)
        map_rv_choice.layoutManager = layManager
        adapter.setChoice(this)
        adapter.setGuidechoice(this)
        val behavior1 = BottomSheetBehavior.from(bottom_sheet1)
        val behavior2 = BottomSheetBehavior.from(ns_search_dialog)
        behavior1.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    STATE_HIDDEN -> behavior1.state = STATE_COLLAPSED
                }
            }

        })
        behavior2.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {

            }

            override fun onStateChanged(p0: View, p1: Int) {
                when (p1) {
                    STATE_COLLAPSED -> behavior2.state = STATE_HIDDEN
                }
            }
        })

        iv_fragment_food_back.setOnClickListener { view ->
            behavior2.state = STATE_HIDDEN

        }
        et_search.addTextChangedListener(this)
        et_search.onItemClickListener =
            AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                lat = InputTipUtils.getTipList()?.get(p2)?.point?.latitude
                lng = InputTipUtils.getTipList()?.get(p2)?.point?.longitude
                LogUtils.log_d<Drawable>(lat.toString() + lng.toString())
                lng?.let { lat?.let { it1 -> drawMarker(it1, it, "测试", "qeqweqwe") } }
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
                poiSearch()
            }
        //驾车导航
//        bs_rb_drive_car.setOnClickListener { view ->
//            context?.let { GudieUtils.startGuilde(it) }
//        }
        fab_fragment_map_weather.setOnClickListener { view ->
//            cityString?.let { context?.let { it1 -> WeatherSearchUtils.weatherSearch(it, it1) } }
            val intent = Intent(this.activity, WeatherActivity::class.java)
            intent.putExtra("cityname", cityString)
            startActivity(intent)
        }
        //点击事件
        aMap?.setOnMarkerClickListener {
            Log.e("maker", it.title.toString())
            return@setOnMarkerClickListener false
        }
        //拖拽事件
        aMap?.setOnMarkerDragListener(object : AMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(p0: Marker?) {
                TODO("Not yet implemented")
            }

            override fun onMarkerDragStart(p0: Marker?) {
                TODO("Not yet implemented")
            }

            override fun onMarkerDrag(p0: Marker?) {
                TODO("Not yet implemented")
            }
        })
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

    private fun drawMarker(lat: Double, lng: Double, title: String, snippet: String) {
        aMap?.clear(true)
        val markerOptions = MarkerOptions()
            .title(title)
            .snippet(snippet)
            .position(LatLng(lat, lng))
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.amap_end))
            .visible(true)
            .draggable(true)
            .anchor(0.5f, 1f)
            .alpha(0.8f);
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
                    )
                    //获取用户的起始点的位置
                    if (start_point == null) {
                        start_point = LatLonPoint(aMapLocation.latitude, aMapLocation.longitude)
                    }
                    //设置原地址信息
                    _city = aMapLocation.city
                    Toast.toast(buffer.toString())
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

    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {

    }

    override fun onPoiSearched(result: PoiResult?, code: Int) {
        //处理得到的POI兴趣点集合 poiResult
        if (code == 1000) {
            for (poiItem in result?.pois!!) {
                poiList.add(poiItem)
                Log.e("Amap", "搜索到的兴趣点有")
                Log.e(
                    "Amap", "poi title =" + poiItem.getTitle() +
                            "latitude = " + poiItem.getLatLonPoint().getLatitude() +
                            "longitude = " + poiItem.getLatLonPoint().getLongitude()
                )
                drawMarker(poiItem.latLonPoint.latitude, poiItem.latLonPoint.longitude)
            }
            end_point = LatLonPoint(
                result.pois.get(0).latLonPoint.latitude,
                result.pois.get(0).latLonPoint.longitude
            )
            endlat=result.pois.get(0).latLonPoint.latitude
            endlng=result.pois.get(0).latLonPoint.longitude
            if (route) {
                drawDrivingRouteLine()
            } else {
                drawWalkingRouteLine()
            }

        } else {
            Toast.toast("未查询到结果")
        }
    }

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                val layoutManager = LinearLayoutManager(context)
                rv_fragment_food_down.layoutManager = layoutManager
//                val serviceAdapter = SurroundingServiceAdapter(surrlat, surlng, context, poiList)
//                rv_fragment_food_down.adapter = serviceAdapter
//                serviceAdapter?.notifyDataSetChanged()
                for (poitem in poiList) {
                    val markerOption = MarkerOptions();
                    markerOption.position(
                        LatLng(
                            poitem.latLonPoint.latitude,
                            poitem.latLonPoint.longitude
                        )
                    )
                        .draggable(false).title(poitem.title.toString()).setFlat(true).visible(true)
                        .infoWindowEnable(true)
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

    fun drawDrivingRouteLine() {
        //1 创建路径的绘制的句柄 routeSearch
        val routeSearch = RouteSearch(MyApplication.context);
        val ft = com.amap.api.services.route.RouteSearch.FromAndTo(start_point, end_point)
        //设置一个路径搜索的query
        val query =
            RouteSearch.DriveRouteQuery(ft, RouteSearch.DRIVING_SINGLE_DEFAULT, null, null, "")
        // 3 给绘制路径的句柄设置一个callback函数
        routeSearch.setRouteSearchListener(object : RouteSearch.OnRouteSearchListener {
            override fun onBusRouteSearched(p0: BusRouteResult?, p1: Int) {
                TODO("Not yet implemented")
            }

            override fun onDriveRouteSearched(p0: DriveRouteResult?, p1: Int) {
                if (p1 != 1000) {
                    LogUtils.log_e<String>("搜索路径失败")
                    return
                }
                //画出路径
                val path = p0?.paths?.get(0)
                val drivingRouteOverlay = DrivingRouteOverlay(
                    MyApplication.context, aMap, path,
                    start_point, end_point, null
                )
                //删除之前的路径
                aMap?.clear()
                //以适当的缩放显示路径
                drivingRouteOverlay.zoomToSpan()
                //去掉中间转弯的一些图标提示
                drivingRouteOverlay.setNodeIconVisibility(false);
                drivingRouteOverlay.setThroughPointIconVisibility(false)
                //将路径添加到地图
                drivingRouteOverlay.addToMap()
                val dis = path?.distance
                val dur = path?.duration
                val des = dur?.toInt()?.let { AMapUtil.getFriendlyTime(it) } +
                        "(" + dis?.toInt()?.let { AMapUtil.getFriendlyLength(it) } + ")"
                Log.e("car", des)

            }

            override fun onRideRouteSearched(p0: RideRouteResult?, p1: Int) {
                TODO("Not yet implemented")
            }

            override fun onWalkRouteSearched(p0: WalkRouteResult?, p1: Int) {
                TODO("Not yet implemented")
            }
        })
        //3 启动路径所有 将query穿进去 向服务器发送请求
        routeSearch.calculateDriveRouteAsyn(query);
    }

    fun drawWalkingRouteLine() {
        //1 创建路径的绘制的句柄 routeSearch
        val routeSearch = RouteSearch(MyApplication.context);
        val ft = com.amap.api.services.route.RouteSearch.FromAndTo(start_point, end_point)
        val query = RouteSearch.WalkRouteQuery(ft, RouteSearch.WALK_DEFAULT)
        routeSearch.setRouteSearchListener(object : RouteSearch.OnRouteSearchListener {
            override fun onDriveRouteSearched(p0: DriveRouteResult?, p1: Int) {
                TODO("Not yet implemented")
            }

            override fun onBusRouteSearched(p0: BusRouteResult?, p1: Int) {
                TODO("Not yet implemented")
            }

            override fun onRideRouteSearched(p0: RideRouteResult?, p1: Int) {
                TODO("Not yet implemented")
            }

            override fun onWalkRouteSearched(p0: WalkRouteResult?, p1: Int) {
                if (p1 != 1000) {
                    LogUtils.log_e<String>("搜索路径失败")
                    return
                }
                val path = p0?.paths?.get(0)

                val walkRouteOverlay = WalkRouteOverlay(
                    MyApplication.context, aMap, path,
                    start_point, end_point
                )
                //删除之前的路径
                aMap?.clear()
                walkRouteOverlay.zoomToSpan()
                walkRouteOverlay.addToMap()
            }
        })
        //3 启动路径所有 将query穿进去 向服务器发送请求
        routeSearch.calculateWalkRouteAsyn(query);
    }

    fun poiSearch() {
        val dstAddr = et_search.text.toString()
        // 1 创建一个搜索的条件对象 query
        val query = PoiSearch.Query(dstAddr, "", _city)
        // 2 创建一个POISearch句柄和query关联
        val poiSearch = PoiSearch(MyApplication.context, query)
        // 3 给search绑定一个回调函数
        poiSearch.setOnPoiSearchListener(this)
        // 4 启动搜索
        poiSearch.searchPOIAsyn();
    }
}


