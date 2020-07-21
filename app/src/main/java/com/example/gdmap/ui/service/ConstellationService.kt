package com.example.gdmap.ui.service

import com.example.gdmap.database.PlayDataBase
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface ConstellationService
{
    @GET("http://api.tianapi.com/txapi/xingzuo/index?key=76728949620b64914233d0e611b30a7f")
    fun getConstellationData(@Query("me")me:String): Observable<PlayDataBase>
}