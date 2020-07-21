package com.example.gdmap.ui.service

import com.example.gdmap.database.ArticleDataBase
import retrofit2.http.GET
import rx.Observable

interface ArticleService {
//    &key=2822a0ad1785599278667f3ca61b1290
    @GET("index?type=top")
    fun getArticleData():Observable<ArticleDataBase>
}