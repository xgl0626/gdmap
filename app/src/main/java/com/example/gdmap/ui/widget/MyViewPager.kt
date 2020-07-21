package com.example.gdmap.ui.widget

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.method.MovementMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import androidx.viewpager.widget.ViewPager

class MyViewPager(context: Context,attributeSet: AttributeSet):ViewPager(context,attributeSet) {

    override fun canScroll(v: View?, checkV: Boolean, dx: Int, x: Int, y: Int): Boolean {
        if(v?.javaClass?.name.equals("com.amap.api.maps.MapView")) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y)
    }
}