package com.example.gdmap.database

 data class VideoDataBase(val code:String,val message:String,val result:List<Result>) {
     data class Result(val data:Data)
     data class Data(val title:String,val description:String,val playUrl:String,val cover:Cover)
     data class Cover(val feed:String,val detail:String)
 }