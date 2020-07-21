package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gdmap.database.ArticleDataBase
import com.example.gdmap.ui.GetArticleData
import com.example.gdmap.ui.Repository
import com.example.gdmap.utils.LogUtils

class ArticleViewModel :ViewModel()
{
    fun getArticleData(getArticleData: GetArticleData) {
        Repository.getArticleData(getArticleData)
    }
}