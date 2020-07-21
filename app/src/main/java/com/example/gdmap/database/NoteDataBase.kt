package com.example.gdmap.database

import android.content.Context
import androidx.room.*
import com.example.gdmap.utils.MyApplication.Companion.context

@Database(version=1,entities=[NotesBook::class])
abstract class NoteDataBase:RoomDatabase() {
   abstract fun noteDao():NoteDao
    companion object{
        private var instance:NoteDataBase?=null

        @Synchronized
        fun getDatabase(context: Context):NoteDataBase
        {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,NoteDataBase::class.java,"app_database")
                .build().apply {
                    instance=this
                }
        }
    }
}