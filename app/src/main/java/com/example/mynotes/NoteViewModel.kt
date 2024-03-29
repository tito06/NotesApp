package com.example.mynotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.db.NoteDao
import com.example.mynotes.db.NotesEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val notesDao: NoteDao) :ViewModel() {

    val allNotes: Flow<List<NotesEntity>> = notesDao.getAllNotes()



    fun insert(note:NotesEntity) = viewModelScope.launch{
        notesDao.insert(note)
    }

    fun update(note: NotesEntity) = viewModelScope.launch {
        notesDao.update(note)
    }

    fun delete(note: NotesEntity) = viewModelScope.launch {
        notesDao.deleteNote(note)
    }
}