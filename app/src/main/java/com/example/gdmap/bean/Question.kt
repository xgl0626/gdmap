package com.example.gdmap.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
    @SerializedName("answer_num")
    val answer_num: Int,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("deleted_at")
    val deleted_at: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("is_like")
    val is_like: Int,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("photo_avatar")
    val photo_avatar: String,
    @SerializedName("photo_url")
    val photo_url: List<String>,
    @SerializedName("place")
    val place: String,
    @SerializedName("question_id")
    val question_id: Int,
    @SerializedName("tittle")
    val tittle: String,
    @SerializedName("updated_at")
    val updated_at: String
) : Serializable