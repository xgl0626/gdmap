package com.example.gdmap.database

data class ArticleDataBase(val reason:String,val result:Result)
{
data class Result(val stat:Int,val data:List<Data>)
data class Data(val title:String,val date:String,val category:String,val author_name:String,val url:String,
                val thumbnail_pic_s:String,val thumbnail_pic_s02:String,val thumbnail_pic_s03:String,
                val thumbnail_pic_s04:String)
}