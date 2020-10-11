package com.example.gdmap.network

import com.example.gdmap.bean.*
import okhttp3.MultipartBody
import retrofit2.http.*
import rx.Observable
import rx.Observer
import java.io.File

/**
 * @Author: xgl
 * @ClassName: ApiService
 * @Description:
 * @Date: 2020/10/11 12:52
 */
interface ApiService {
    @Multipart
    @POST("/question/addQuestion")
    fun addQuestion(
        @Header("Authorization") token: String,
        @Query("title") title: String,
        @Query("place") place: String,
        @Query("description") description: String,
        @Part("photo") photoList: List<MultipartBody.Part>
    ): Observable<ResponseStatus>

    @GET("/question/getQuestionInfo")
    fun getQuestionContent(@Field("question_id") question_id: Int): Observable<Question>


    @FormUrlEncoded
    @POST("/question/search")
    fun search(@Field("title") title: String): Observable<Search>

    @Multipart
    @POST("/answer/addAnswer")
    fun addAnswer(
        @Header("Authorization") token: String,
        @Query("title") title: String,
        @Query("description") description: String,
        @Part("photo") photoList: List<MultipartBody.Part>
    ): Observable<ResponseStatus>

    @GET("/answer/getAnswerList")
    fun getAnswerList(
        @Header("Authorization") token: String,
        @Field("question_id") question_id: Int
    ): Observable<MutableList<Answer>>

    @FormUrlEncoded
    @POST("/comment/addComment")
    fun addComment(
        @Header("Authorization") token: String,
        @Field("answer_id") answer_id: Int,
        @Field("description") description: String
    ): Observable<ResponseStatus>

    @GET("/comment/getCommentList")
    fun getCommentList(@Header("Authorization") token: String,
                       @Field("answer_id") answer_id: Int):Observable<Comment>

    @FormUrlEncoded
    @POST("/like ")
    fun like(
        @Header("Authorization") token: String,
        @Field("id") id: Int,
        @Field("type") type: String
    ):Observable<ResponseStatus>

    @FormUrlEncoded
    @POST("/collect")
    fun collect(@Header("Authorization") token: String,
                @Field("id") id: Int):Observable<ResponseStatus>
}