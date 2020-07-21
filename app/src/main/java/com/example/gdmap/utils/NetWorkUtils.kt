package com.example.gdmap.utils

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetWorkUtils {
    var BASEURL="https://api.apiopen.top/"
    var BASEURL2="http://v.juhe.cn/toutiao/"
    val BASEURL3="http://api.tianapi.com/txapi/"
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://v.juhe.cn/toutiao/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build()
    fun <T>create(serviceClass:Class<T>): T? = retrofit?.create(serviceClass)
}