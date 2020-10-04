package com.example.gdmap.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gdmap.R

/**
 * @Author: xgl
 * @ClassName: Context
 * @Description:
 * @Date: 2020/10/4 17:55
 */

//returns dip(dp) dimension value in pixels
fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()

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