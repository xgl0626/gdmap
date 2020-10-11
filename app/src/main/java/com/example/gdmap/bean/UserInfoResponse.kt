package com.example.gdmap.bean

/**
 *@Date 2020/10/11
 *@Time 16:15
 *@author SpreadWater
 *@description获取用户信息返回的bean类
 */
data class UserInfoResponse(val data:MyUser,val info:String,val status:Int )
data class MyUser(
        val user_id:String,
        val nickname:String,
        val description:String,
        val qq:String,
        val tel:String,
        val user_status:String,
        val avatar:String
)