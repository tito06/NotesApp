package com.example.mynotes.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: NotesEntity)

    @Update
    suspend fun update(note: NotesEntity)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NotesEntity>>


}