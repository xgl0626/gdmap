package com.example.gdmap.network

import com.example.gdmap.bean.UpdateInformation
import com.example.gdmap.bean.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import rx.Observable

/**
 *@Date 2020/10/11
 *@Time 15:14
 *@author SpreadWater
 *@description
 */
interface UpdateInfo {
    //更新用户信息
    @POST("/user/updateUserInfo")
    @FormUrlEncoded
    fun updateInfo(
        @Header("Authorization") token: String,
        @Field("qq") qq: String,
        @Field("tel") tel: String,
        @Field("description") description: String,
        @Field("nickname") nickname: String
    ): Observable<UpdateInformation>
}