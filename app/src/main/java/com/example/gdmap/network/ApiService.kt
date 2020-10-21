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

    @POST("/question/getQuestionListLatest")
    @FormUrlEncoded
    fun getQuestionList(@Header("Authorization") token: String,@Field("page") page:Int): Observable<QuestionList>

    @POST("/question/addQuestion")
    @Multipart
    fun addQuestion(
        @Header("Authorization") token: String,
        @Part page: List<MultipartBody.Part>
    ): Observable<ResponseStatus>

    @POST("/question/getQuestionInfo")
    @FormUrlEncoded
    fun getQuestionContent(@Field("question_id") question_id: Int): Observable<Question>


    @POST("/question/search")
    @FormUrlEncoded
    fun search(@Field("title") title: String): Observable<Search>

    @POST("/answer/addAnswer")
    @FormUrlEncoded
    fun addAnswer(
        @Header("Authorization") token: String,
        @Field("question_id") question_id: Int,
        @Field("description") description: String
    ): Observable<ResponseStatus>

    @POST("/answer/getAnswerList")
    @FormUrlEncoded
    fun getAnswerList(
        @Header("Authorization") token: String,
        @Field("question_id") question_id: Int
    ): Observable<Answer>

    @POST("/comment/addComment")
    @FormUrlEncoded
    fun addComment(
        @Header("Authorization") token: String,
        @Field("answer_id") answer_id: Int,
        @Field("description") description: String
    ): Observable<ResponseStatus>

    @POST("/comment/getCommentList")
    @FormUrlEncoded
    fun getCommentList(
        @Header("Authorization") token: String,
        @Field("answer_id") answer_id: Int
    ): Observable<Comment>

    @POST("/like")
    @FormUrlEncoded
    fun like(
        @Header("Authorization") token: String,
        @Field("id") id: Int,
        @Field("type") type: String
    ): Observable<ResponseStatus>


    @POST("/collect")
    @FormUrlEncoded
    fun collect(
        @Header("Authorization") token: String,
        @Field("id") id: Int
    ): Observable<ResponseStatus>

    @POST("/user/getUserInfo")
    fun getInfo(@Header("Authorization") token: String): Observable<UserInfoResponse>

    @GET("/question/getCollectedQuestion")
    fun getCollectQuestionList():Observable<Question>
}