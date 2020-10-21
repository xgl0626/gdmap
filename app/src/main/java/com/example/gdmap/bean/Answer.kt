package com.example.gdmap.bean

/**
 * @Author: xgl
 * @ClassName: Answer
 * @Description:
 * @Date: 2020/10/11 14:28
 */
data class Answer(
    val `data`: List<AnswerData>,
    val info: String,
    val status: Int
)

data class AnswerData(
    val replyList:ArrayList<CommentData>?=null,
    val answer_id: Int,
    val created_at: String,
    val deleted_at: String,
    val description: String,
    val is_like: Int,
    val nickname: String,
    val photo_avatar: String,
    val photo_url: List<String>,
    val updated_at: String
)
