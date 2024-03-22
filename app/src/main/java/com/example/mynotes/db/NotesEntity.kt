package com.example.mynotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class NotesEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val title:String,
    val content:String
)