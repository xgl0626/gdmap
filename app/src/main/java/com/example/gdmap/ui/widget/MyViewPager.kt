package com.example.gdmap.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

class MyViewPager(context: Context, attributeSet: AttributeSet) : ViewPager(context, attributeSet) {

    override fun canScroll(v: View?, checkV: Boolean, dx: Int, x: Int, y: Int): Boolean {
        if (v?.javaClass?.name.equals("com.amap.api.maps.MapView")) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y)
    }
}