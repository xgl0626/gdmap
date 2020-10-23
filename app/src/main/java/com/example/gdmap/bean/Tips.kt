package com.example.gdmap.bean

/**
 *@Date 2020-10-23
 *@Time 21:31
 *@author SpreadWater
 *@description
 */
data class Tips(
    val `data`: List<Tip>,
    val info: String,
    val status: Int
)
data class Tip(
    val description: String,
    val id:Int
)