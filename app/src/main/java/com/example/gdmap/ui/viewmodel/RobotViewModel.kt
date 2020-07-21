package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gdmap.ui.GetRobotData
import com.example.gdmap.ui.Repository

class RobotViewModel :ViewModel() {
    fun getRobotData(question:String,data:GetRobotData)
    {
        Repository.getRobotData(question,data)
    }
}