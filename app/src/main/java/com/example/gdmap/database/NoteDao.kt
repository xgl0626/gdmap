package com.example.gdmap.database

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    fun insertNotes(note:NotesBook):Long
    @Update
    fun updateNotes(note: NotesBook)
    @Query("select*from NotesBook")
    fun loadAllNotes():List<NotesBook>

    @Query("select*from NotesBook where id =:id")
    fun getNotesBookByName(id: Long): NotesBook
    @Delete
    fun deleteNotes(note: NotesBook)
}