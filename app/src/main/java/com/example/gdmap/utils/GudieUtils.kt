package com.example.gdmap.utils

import android.content.Context
import android.view.View
import com.amap.api.maps.model.Poi
import com.amap.api.navi.*
import com.amap.api.navi.model.AMapNaviLocation

object GudieUtils : INaviInfoCallback {
    fun startGuilde(context: Context,start:Poi,end:Poi) {
        val amapNaviParams = AmapNaviParams(start,null,end, AmapNaviType.DRIVER)
        //设置是否多条路径
        amapNaviParams.isMultipleRouteNaviMode = true
        //设置是否语音播报
        amapNaviParams.setUseInnerVoice(true)
        //设置启动导航组件是否进行算路
        amapNaviParams.isNeedCalculateRouteWhenPresent = true
        //设置退出导航组件是否销毁导航实例
        amapNaviParams.isNeedDestroyDriveManagerInstanceWhenNaviExit = true

        amapNaviParams.theme = (
                AmapNaviTheme.WHITE)
        AmapNaviPage.getInstance().showRouteActivity(
            context, amapNaviParams, this)
    }

    override fun onGetNavigationText(p0: String?) {

    }

    override fun onCalculateRouteSuccess(p0: IntArray?) {

    }

    override fun onInitNaviFailure() {
    }

    override fun onStrategyChanged(p0: Int) {
    }

    override fun onScaleAutoChanged(p0: Boolean) {
    }

    override fun onReCalculateRoute(p0: Int) {
    }

    override fun getCustomNaviView(): View {
        TODO("Not yet implemented")
    }


    override fun onDayAndNightModeChanged(p0: Int) {
    }

    override fun onCalculateRouteFailure(p0: Int) {
    }

    override fun getCustomMiddleView(): View {
        TODO("Not yet implemented")
    }


    override fun onMapTypeChanged(p0: Int) {
    }

    override fun onLocationChange(p0: AMapNaviLocation?) {
    }

    override fun getCustomNaviBottomView(): View {
        TODO("Not yet implemented")
    }


    override fun onArrivedWayPoint(p0: Int) {
    }

    override fun onArriveDestination(p0: Boolean) {
    }

    override fun onStartNavi(p0: Int) {
    }

    override fun onStopSpeaking() {
    }

    override fun onExitPage(p0: Int) {
    }

    override fun onNaviDirectionChanged(p0: Int) {
    }

    override fun onBroadcastModeChanged(p0: Int) {
    }

}