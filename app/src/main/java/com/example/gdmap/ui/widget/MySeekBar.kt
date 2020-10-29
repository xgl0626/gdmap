package com.example.gdmap.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

class MySeekBar(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatSeekBar(context, attrs) {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }
}