package com.example.gdmap.utils

import android.util.Log

object LogUtils {
    val logSwitch = true
    val TAG = "errorabc"
    inline fun <reified T> log_i(log: Any): String {
        val resultI = if (logSwitch) {
            Log.i(T::class.simpleName, log.toString())
            "OK"
        } else {
            "fail"
        }
        return resultI
    }

    inline fun <reified T> log_d(log: Any): String {
        val resultD = if (logSwitch) {
            Log.d(TAG, log.toString())
            "OK"
        } else {
            "fail"
        }
        return resultD
    }

    inline fun <reified T> log_w(log: Any): String {
        val resultW = if (logSwitch) {
            Log.w(T::class.simpleName, log.toString())
            "OK"
        } else {
            "fail"
        }
        return resultW
    }

    inline fun <reified T> log_e(log: Any): String {
        val resultE = if (logSwitch) {
            Log.e(T::class.simpleName, log.toString())
            "OK"
        } else {
            "fail"
        }
        return resultE
    }

}