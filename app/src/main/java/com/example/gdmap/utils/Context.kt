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
