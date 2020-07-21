package com.example.gdmap.ui

import com.example.gdmap.database.PlayDataBase
import com.example.gdmap.database.RobotDataBase

interface GetPlayData {
    fun success(data: PlayDataBase)
    fun failure()
}