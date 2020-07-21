package com.example.gdmap.utils

import android.widget.Toast

object ToastUtils {
    fun String.showToast(duration:Int = Toast.LENGTH_SHORT){
        Toast.makeText(MyApplication.context,this,duration).show()
    }
    fun Int.showToast(duration:Int = Toast.LENGTH_SHORT){
        Toast.makeText(MyApplication.context,this,duration).show()
    }
}