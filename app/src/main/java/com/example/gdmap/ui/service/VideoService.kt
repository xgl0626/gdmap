package com.example.gdmap.ui.service

import com.example.gdmap.database.VideoDataBase
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface VideoService {
    @GET("https://api.apiopen.top/videoRecommend?id=127398")
    fun getVideoSource():Observable<VideoDataBase>
}