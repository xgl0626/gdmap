package com.example.gdmap.bean

/**
 * @Author: xgl
 * @ClassName: RedirectBean
 * @Description:
 * @Date: 2020/10/26 21:31
 */
data class RedirectBean(
    val `data`: RedirectData,
    val info: String,
    val status: Int
)

data class RedirectData(
    val photo_url: String
)