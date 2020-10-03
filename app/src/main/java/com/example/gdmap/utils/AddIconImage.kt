package com.example.gdmap.utils

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

object AddIconImage {
    private val LEFT = 0
    private val RIGHT = 1
    private val TOP = 2
    private val BOTTOM = 3
    fun setImageViewToButton(drawable: Int, view: Button, place: Int) {
        val drawable: Drawable =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    MyApplication.context.resources.getDrawable(drawable, null)
                } else {
                    TODO("VERSION.SDK_INT < LOLLIPOP")
                }
        drawable.setBounds(0, 0, 80, 65)
        when (place) {
            TOP -> view.setCompoundDrawables(null, drawable, null, null)
            RIGHT -> view.setCompoundDrawables(null, null, drawable, null)
            LEFT -> view.setCompoundDrawables(drawable, null, null, null)
            BOTTOM -> view.setCompoundDrawables(null, null, drawable, drawable)
        }
    }

    fun setImageViewToEditText(drawable: Int, view: EditText, place: Int) {
        val drawable: Drawable =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    MyApplication.context.resources.getDrawable(drawable, null)
                } else {
                    TODO("VERSION.SDK_INT < LOLLIPOP")
                }
        drawable.setBounds(0, 0, 60, 60)
        when (place) {
            TOP -> view.setCompoundDrawables(null, drawable, null, null)
            RIGHT -> view.setCompoundDrawables(null, null, drawable, null)
            LEFT -> view.setCompoundDrawables(drawable, null, null, null)
            BOTTOM -> view.setCompoundDrawables(null, null, drawable, drawable)
        }
    }

    fun setImageViewToTextView(drawable: Int, view: TextView, place: Int) {
        val drawable: Drawable =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                MyApplication.context.resources.getDrawable(drawable, null)
            } else {
                TODO("VERSION.SDK_INT < LOLLIPOP")
            }
        drawable.setBounds(0, 0, 80, 80)
        when (place) {
            TOP -> view.setCompoundDrawables(null, drawable, null, null)
            RIGHT -> view.setCompoundDrawables(null, null, drawable, null)
            LEFT -> view.setCompoundDrawables(drawable, null, null, null)
            BOTTOM -> view.setCompoundDrawables(null, null, drawable, drawable)
        }
    }
}