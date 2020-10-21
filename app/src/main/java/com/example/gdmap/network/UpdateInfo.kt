package com.example.gdmap.network

import com.example.gdmap.bean.UpdateInformation
import com.example.gdmap.bean.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.*
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
    @Multipart
    fun updateInfo(
        @Header("Authorization") token: String,
        @Part page: List<MultipartBody.Part>
    ): Observable<UpdateInformation>
}