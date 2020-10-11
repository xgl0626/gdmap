package com.example.gdmap.bean

/**
 *@Date 2020/10/11
 *@Time 12:41
 *@author SpreadWater
 *@description 登录注册的信息bean类
 *
 */
data class UserResponse(val status:Int, val info:String, val data:UserInformation)

data class UserInformation(
    val description:String,
    val nickname :String,
    val qq:String,
    val token:String,
    val userId:Int,
    val userStatus:String
)