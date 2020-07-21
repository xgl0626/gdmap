package com.example.gdmap.ui.service

import com.example.gdmap.database.PlayDataBase
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface HistoryService {
    @GET("http://api.tianapi.com/txapi/lishi/index?key=76728949620b64914233d0e611b30a7f")
    fun getHistoryData(@Query("date")date:String):Observable<PlayDataBase>
}