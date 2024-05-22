package com.example.mynotes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mynotes.Converters


@Database(entities = [NotesEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class NotesDb: RoomDatabase() {

    abstract fun notesDao():NoteDao


}