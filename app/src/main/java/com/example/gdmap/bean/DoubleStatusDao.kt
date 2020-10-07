package com.example.gdmap.bean

import android.content.Context
import androidx.core.content.edit
import com.example.gdmap.utils.MyApplication

/**
 *@date 2020-8-15
 *@author zhangsan
 *@description
 */
object DoubleStatusDao {
    /*
        用于保存收藏的id和状态
     */
    //placeid存储Collect的状态
    fun saveStatus(placeid: Int, status: Boolean) {
        sharedPreferences().edit {
            putBoolean(placeid.toString(), status)
        }
    }

    //placeid获取Collect的状态
    fun getStatus(placeid: Int) = sharedPreferences().getBoolean(placeid.toString(), false)


    private fun sharedPreferences() = MyApplication.context.getSharedPreferences("ImageButton_status_place", Context.MODE_PRIVATE)
}