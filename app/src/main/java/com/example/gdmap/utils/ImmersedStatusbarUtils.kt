package com.example.gdmap.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager

object ImmersedStatusbarUtils {
    fun initSetContentView(activivty: Activity, titleViewGroup: View?)
    {
       if(activivty== null)
       {
           return
       }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
        {
            val window=activivty.window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if(titleViewGroup==null)
        {
            return

        }
        val statusBarHeight= getStatusBarHeight(activivty)
        titleViewGroup.setPadding(0,statusBarHeight,0,0)
    }
    private fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId: Int = context.resources.getIdentifier(
            "status_bar_height", "dimen", "android"
        )
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

}