package com.example.gdmap.bean

/**
 * @Author: xgl
 * @ClassName: Question
 * @Description:
 * @Date: 2020/10/11 14:27
 */

data class Question(
    val `data`: QuestionData,
    val info: String,
    val status: Int
)

data class QuestionData(
    val answer_num: Int,
    val created_at: String,
    val deleted_at: String,
    val description: String,
    val is_like: Int,
    val nickname: String,
    val photo_avatar: String,
    val photo_url: List<String>,
    val place: String,
    val question_id: Int,
    val tittle: String,
    val updated_at: String
)