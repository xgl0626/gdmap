package com.example.gdmap.ui

import com.example.gdmap.database.ArticleDataBase

interface GetArticleData {
    fun success(data: List<ArticleDataBase.Data>)
    fun failure()
}