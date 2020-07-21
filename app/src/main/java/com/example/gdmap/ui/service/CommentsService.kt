package com.example.gdmap.ui.service

import com.example.gdmap.database.PlayDataBase
import retrofit2.http.GET
import rx.Observable

interface CommentsService {
    @GET("http://api.tianapi.com/txapi/hotreview/index?key=76728949620b64914233d0e611b30a7f")
    fun getCommentsData(): Observable<PlayDataBase>
}