package com.example.gdmap.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.amap.api.navi.AMapNavi
import com.amap.api.navi.AMapNaviListener
import com.amap.api.navi.AMapNaviViewListener
import com.amap.api.navi.enums.NaviType
import com.amap.api.navi.model.*
import com.autonavi.tbt.TrafficFacilityInfo
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import kotlinx.android.synthetic.main.activity_timegulide.*
import java.lang.Exception


class GudieActivity:BaseActivity(), AMapNaviListener,AMapNaviViewListener {
    private var endlng: Double=0.0
    private var endlat: Double=0.0
    private var startlat: Double=0.0
    private var startlng: Double=0.0
    private var amapniv:AMapNavi?=null
    private var choice:Boolean=false
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        startlat = intent.getDoubleExtra("lat", 0.0)
        startlng = intent.getDoubleExtra("lng", 0.0)
        endlat = intent.getDoubleExtra("endlat",0.0)
        endlng = intent.getDoubleExtra("endlng",0.0)
        choice=intent.getBooleanExtra("choice",false)
        navi_view.onCreate(savedInstanceState)
        navi_view.setAMapNaviViewListener(this)
        amapniv=AMapNavi.getInstance(applicationContext)
        amapniv?.setUseInnerVoice(true)
        amapniv?.addAMapNaviListener(this)

    }

    override fun initView() {

    }

    override fun initClick() {
    }

    override fun initData() {
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_timegulide
    }

    override fun onResume() {
        super.onResume()
        navi_view.onResume();
    }

    override fun onPause() {
        super.onPause()
        navi_view.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
        navi_view.onDestroy()
        amapniv?.stopNavi()
        amapniv?.destroy()
    }

    override fun onNaviInfoUpdate(p0: NaviInfo?) {
    }

    override fun onCalculateRouteSuccess(p0: IntArray?) {
        amapniv?.startNavi(NaviType.GPS)
    }

    override fun onCalculateRouteSuccess(p0: AMapCalcRouteResult?) {
        amapniv?.startNavi(NaviType.GPS)
    }

    override fun onCalculateRouteFailure(p0: Int) {
    }

    override fun onCalculateRouteFailure(p0: AMapCalcRouteResult?) {
    }

    override fun onServiceAreaUpdate(p0: Array<out AMapServiceAreaInfo>?) {
    }

    override fun onGpsSignalWeak(p0: Boolean) {
    }

    override fun onEndEmulatorNavi() {
    }

    override fun onArrivedWayPoint(p0: Int) {
    }

    override fun onArriveDestination() {
    }

    override fun onPlayRing(p0: Int) {
    }

    override fun onTrafficStatusUpdate() {
    }

    override fun onGpsOpenStatus(p0: Boolean) {
    }
    override fun updateAimlessModeCongestionInfo(p0: AimLessModeCongestionInfo?) {
    }

    override fun showCross(p0: AMapNaviCross?) {
    }

    override fun onGetNavigationText(p0: Int, p1: String?) {
    }

    override fun onGetNavigationText(p0: String?) {
    }

    override fun updateAimlessModeStatistics(p0: AimLessModeStat?) {
    }

    override fun hideCross() {
    }

    override fun onInitNaviFailure() {
    }

    override fun onInitNaviSuccess() {
        var strategy = 0;
        try {
            strategy = amapniv?.strategyConvert(true, false, false, true, true)!!
        } catch (e:Exception) {
            e.printStackTrace();
        }
        if (choice){
            amapniv?.calculateWalkRoute(startlat?.let { startlng?.let { it1 -> NaviLatLng(it, it1) } },
                endlat?.let { endlng?.let { it1 -> NaviLatLng(it, it1) } })
        }else{
            val startLatLng=NaviLatLng(startlat,startlng)
            val endLatLng=NaviLatLng(endlat,endlng)
            val sList=ArrayList<NaviLatLng>()
            val eList=ArrayList<NaviLatLng>()
            sList.add(startLatLng)
            eList.add(endLatLng)
            amapniv?.calculateDriveRoute(sList,eList,null, strategy);//起始点坐标、终点坐标、途经点坐标、strategy
        }
    }

    override fun onReCalculateRouteForTrafficJam() {
    }

    override fun updateIntervalCameraInfo(
        p0: AMapNaviCameraInfo?,
        p1: AMapNaviCameraInfo?,
        p2: Int
    ) {
    }

    override fun hideLaneInfo() {
    }

    override fun onNaviInfoUpdated(p0: AMapNaviInfo?) {
    }

    override fun showModeCross(p0: AMapModelCross?) {
    }

    override fun updateCameraInfo(p0: Array<out AMapNaviCameraInfo>?) {
    }

    override fun hideModeCross() {
    }

    override fun onLocationChange(p0: AMapNaviLocation?) {
    }

    override fun onReCalculateRouteForYaw() {
    }

    override fun onStartNavi(p0: Int) {
    }

    override fun notifyParallelRoad(p0: Int) {
    }

    override fun OnUpdateTrafficFacility(p0: Array<out AMapNaviTrafficFacilityInfo>?) {
    }

    override fun OnUpdateTrafficFacility(p0: AMapNaviTrafficFacilityInfo?) {
    }

    override fun OnUpdateTrafficFacility(p0: TrafficFacilityInfo?) {
    }

    override fun onNaviRouteNotify(p0: AMapNaviRouteNotifyData?) {
    }

    override fun showLaneInfo(p0: Array<out AMapLaneInfo>?, p1: ByteArray?, p2: ByteArray?) {
    }

    override fun showLaneInfo(p0: AMapLaneInfo?) {
    }

    override fun onNaviTurnClick() {
    }

    override fun onScanViewButtonClick() {
    }

    override fun onLockMap(p0: Boolean) {
    }

    override fun onMapTypeChanged(p0: Int) {
    }

    override fun onNaviCancel() {
        finish()
    }

    override fun onNaviViewLoaded() {
    }

    override fun onNaviBackClick(): Boolean {
        return false
    }

    override fun onNaviMapMode(p0: Int) {
    }

    override fun onNextRoadClick() {
    }

    override fun onNaviViewShowMode(p0: Int) {
    }

    override fun onNaviSetting() {
    }
}

