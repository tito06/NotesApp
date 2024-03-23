package com.example.mynotes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [NotesEntity::class], version = 1)
abstract class NotesDb: RoomDatabase() {

    abstract fun notesDao():NoteDao


/*    companion object{
        @Volatile
        private var INSTANCE: NotesDb? = null

        fun getDatabase(context: Context) :NotesDb{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return  tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDb::class.java,
                    "notes"
                ).build()
                INSTANCE = instance
                return  instance
            }
        }
    }*/
}