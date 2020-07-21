package com.example.gdmap.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotesBook (var title:String, var content:String, var time:String)
{
    @PrimaryKey(autoGenerate = true)
    var id:Long=0
}