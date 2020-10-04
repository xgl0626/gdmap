package com.example.gdmap.utils

import android.animation.ValueAnimator
import android.view.View
import kotlinx.android.synthetic.main.fragment_me.*

/**
 * @Author: xgl
 * @ClassName: Animator
 * @Description:
 * @Date: 2020/10/4 16:47
 */
fun pressToZoomOut(view: View) {
    val animScale = ValueAnimator.ofFloat(1f, 0.8f, 1f)
    animScale.duration = 500
    animScale.addUpdateListener {
        view.scaleX = it.animatedValue as Float
        view.scaleY = it.animatedValue as Float
    }
    animScale.start()
}