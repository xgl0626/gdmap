package com.example.gdmap.config

import com.example.gdmap.bean.CommentData
import com.example.gdmap.bean.Token

/**
 * @Author: xgl
 * @ClassName: Token
 * @Description: 把获取到的用户信息全局存一遍方便token使用
 * @Date: 2020/10/11 15:22
 */
object TokenConfig {
    const val BASE_URL = "http://47.93.114.84:8081"
    var token= Token()
    var bindReplyData=HashMap<Int,List<CommentData>>()
}