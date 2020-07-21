package com.example.gdmap.ui.service

import com.example.gdmap.database.RobotDataBase
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface RobotService {
    @GET("http://api.tianapi.com/txapi/tuling/index?key=76728949620b64914233d0e611b30a7f")
    fun getRobotData(@Query("question")question:String):Observable<RobotDataBase>
}