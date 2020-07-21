package com.example.gdmap.database

data class RobotDataBase(val code:Int,val msg:String,val datatype:String,val newslist:List<NewsList>)
{
    data class NewsList(val reply:String)
}
