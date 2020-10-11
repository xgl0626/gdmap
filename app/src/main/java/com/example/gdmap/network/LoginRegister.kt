package com.example.gdmap.network

import com.example.gdmap.bean.UserResponse
import retrofit2.http.*
import rx.Observable

/**
 *@Date 2020/10/11
 *@Time 12:48
 *@author SpreadWater
 *@description
 */
interface LoginRegister {
    //注册
    @POST("/signup")
    @FormUrlEncoded
    fun SignUp(
        @Field("user_id") user_id: String,
        @Field("password") password: String
    ): Observable<UserResponse>

    //登录
    @POST("/signin")
    @FormUrlEncoded
    fun SiginIn(
        @Field("user_id") user_id: String,
        @Field("password") password: String
    ): Observable<UserResponse>

}