package com.example.gdmap.bean

/**
 * @Author: xgl
 * @ClassName: QuestionList
 * @Description:
 * @Date: 2020/10/19 14:28
 */
data class QuestionList(
    val `data`: List<QuestionData>,
    val info: String,
    val status: Int
)