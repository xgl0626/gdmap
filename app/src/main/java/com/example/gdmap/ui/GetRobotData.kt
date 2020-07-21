package com.example.gdmap.ui

import com.example.gdmap.database.ArticleDataBase
import com.example.gdmap.database.RobotDataBase

interface GetRobotData {
    fun success(data: RobotDataBase)
    fun failure()
}