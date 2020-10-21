package com.example.gdmap.utils

import android.animation.ValueAnimator
import android.view.View
import com.example.gdmap.R
import com.example.gdmap.bean.DoubleStatusDao

/**
 * @Author: xgl
 * @ClassName: Animator
 * @Description:
 * @Date: 2020/10/4 16:47
 */
fun View.pressToZoomOut() {
    val animScale = ValueAnimator.ofFloat(1f, 0.8f, 1f)
    animScale.duration = 500
    animScale.addUpdateListener {
        scaleX = it.animatedValue as Float
        scaleY = it.animatedValue as Float
    }
    animScale.start()
}

fun View.excite(viewId: Int) {
        pressToZoomOut()
        if (!DoubleStatusDao.getStatus(viewId)) {
            setBackgroundResource(R.drawable.ic_home_message_black_up)
            DoubleStatusDao.saveStatus(viewId, true)
        } else {
            setBackgroundResource(R.drawable.ic_home_message_up)
            DoubleStatusDao.saveStatus(viewId, false)
        }
}

fun View.favorite(viewId: Int) {
        pressToZoomOut()
        if (!DoubleStatusDao.getStatus(viewId)) {
            DoubleStatusDao.saveStatus(viewId, true)
            setBackgroundResource(R.drawable.ic_fravorite)
        } else {
            DoubleStatusDao.saveStatus(viewId, false)
            setBackgroundResource(R.drawable.ic_unfravorite)
        }
}

fun View.isCollected(viewId: Int) {
    if (DoubleStatusDao.getStatus(viewId)) {
        setBackgroundResource(R.drawable.ic_fravorite)
    } else {
        setBackgroundResource(R.drawable.ic_unfravorite)
    }
}

fun View.isLike(viewId: Int) {
    if (DoubleStatusDao.getStatus(viewId)) {
        setBackgroundResource(R.drawable.ic_home_message_black_up)
    } else {
        setBackgroundResource(R.drawable.ic_home_message_up)
    }
}

/**
 * @param interval 毫秒为单位，点击间隔小于这个值监听事件无法生效
 * @param click 具体的点击事件
 */
fun View.setOnSingleClickListener(interval: Long = 500, click: (View) -> Unit) {
    setOnClickListener {
        val tag = getTag(R.id.common_view_click_time) as? Long
        if (System.currentTimeMillis() - (tag ?: 0L) > interval) {
            click(it)
        }
        it.setTag(R.id.common_view_click_time, System.currentTimeMillis())
    }
}