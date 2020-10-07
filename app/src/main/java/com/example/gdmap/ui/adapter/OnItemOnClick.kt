package com.example.gdmap.ui.adapter

import android.view.View

/**
 * @Author: xgl
 * @ClassName: OnItemOnClick
 * @Description:
 * @Date: 2020/10/7 9:20
 */
interface OnItemOnClick {
    fun onClick(view: View,groupPosition: Int,string: String)
}