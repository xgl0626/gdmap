package com.example.gdmap.database

data class PlayDataBase(val code:String,val msg:String,val newslist:List<NewsList>)
{
    data class NewsList(val title:String,val grade:String,val content:String,val lsdate:String,val source:String)
}