package com.example.gdmap.bean

/**
 *@Date 2020/10/11
 *@Time 15:28
 *@author SpreadWater
 *@description 用户更新数据传入的新的用户信息类
 */
data class UserInfo(
    val description: String,
    val nickname: String,
    val qq: String,
    val tel:String
)