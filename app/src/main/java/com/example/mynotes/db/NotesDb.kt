package com.example.mynotes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [NotesEntity::class], version = 1)
abstract class NotesDb: RoomDatabase() {

    abstract fun notesDao():NoteDao


}