package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gdmap.ui.GetVideoSource
import com.example.gdmap.ui.Repository
import com.example.gdmap.utils.LogUtils

class VideoViewModel: ViewModel() {
    fun getVideoSource(data:GetVideoSource)
    {
        Repository.getVideoSource(data)
    }
}