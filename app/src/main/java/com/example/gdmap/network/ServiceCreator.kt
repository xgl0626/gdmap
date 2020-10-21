package com.example.gdmap.network

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *@Date 2020/10/11
 *@Time 13:38
 *@author SpreadWater
 *@description
 */
object ServiceCreator {

    private const val BASE_URL = "http://47.93.114.84:8081"
    private val retrofit: Retrofit
    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        val OkHttpClient = with(okhttp3.OkHttpClient.Builder()) {
            connectTimeout(5L, TimeUnit.SECONDS)
            writeTimeout(10L, TimeUnit.SECONDS)
            readTimeout(10L, TimeUnit.SECONDS)
            addInterceptor(logging)
            build()
        }
         retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient)
            .build()
    }
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}