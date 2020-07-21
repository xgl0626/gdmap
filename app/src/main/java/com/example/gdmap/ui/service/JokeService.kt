package com.example.gdmap.ui.service

import com.example.gdmap.database.PlayDataBase
import retrofit2.http.GET
import rx.Observable

interface JokeService {
    @GET("http://api.tianapi.com/txapi/joke/index?key=76728949620b64914233d0e611b30a7f&num=10")
    fun getJokeData(): Observable<PlayDataBase>
}