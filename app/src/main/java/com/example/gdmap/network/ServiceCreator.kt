package com.example.gdmap.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *@Date 2020/10/11
 *@Time 13:38
 *@author SpreadWater
 *@description
 */
object ServiceCreator {
    private const val BASE_URL="http://47.93.114.84:8081"
    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T>create(serviceClass:Class<T>):T= retrofit.create(serviceClass)

    inline fun <reified  T>create():T= create(T::class.java)

}