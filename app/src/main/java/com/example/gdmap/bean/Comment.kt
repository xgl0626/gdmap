package com.example.gdmap.bean

/**
 * @Author: xgl
 * @ClassName: Comment
 * @Description:
 * @Date: 2020/10/11 14:28
 */
data class Comment(
    val `data`: List<CommentData>,
    val info: String,
    val status: Int
)

data class CommentData(
    val comment_id: Int,
    val created_at: String,
    val deleted_at: String,
    val description: String,
    val nickname: String,
    val photo_avatar: String?=null,
    val updated_at: String
)