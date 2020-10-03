package com.example.gdmap.database

/**
 * @Author: 徐国林
 * @ClassName: MessagesTestData
 * @Description:
 * @Date: 2020/9/2 16:48
 */
data class MessagesTestData(
    val author: String = "xgl",
    val title: String = "xglxglxgl",
    val content: String = "xlgasfasfasfas",
    val time: String = "2020.8.31.9.:30"
)

data class AnswerTestData(
    val antuor: String = "xlgxlg",
    val content: String = "xlgxlglxlglglgl",
    val time: String = "2020.8.30.9:30"
)
