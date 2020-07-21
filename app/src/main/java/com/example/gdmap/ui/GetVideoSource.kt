package com.example.gdmap.ui

import com.example.gdmap.database.VideoDataBase

interface GetVideoSource {
    fun success(data: List<VideoDataBase.Result>)
    fun failure()
}