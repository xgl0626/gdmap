package com.example.gdmap.bean

/**
 *@Date 2020/10/11
 *@Time 15:20
 *@author SpreadWater
 *@description
 */
data class UpdateInformation(val info: String, val status: Int, val data: Data)
data class Data(val message: String)