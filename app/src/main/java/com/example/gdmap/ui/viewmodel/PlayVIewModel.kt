package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gdmap.ui.GetPlayData
import com.example.gdmap.ui.Repository

class PlayVIewModel :ViewModel()
{
    fun getJoke(data:GetPlayData)
    {
        Repository.getJoke(data)
    }
    fun getHistory(date:String,data:GetPlayData)
    {
        Repository.getHistory(date,data)
    }
    fun getComments(data:GetPlayData)
    {
        Repository.getComments(data)
    }
    fun getConstellation(me:String,data:GetPlayData)
    {
        Repository.getConstellation(me,data)
    }
}