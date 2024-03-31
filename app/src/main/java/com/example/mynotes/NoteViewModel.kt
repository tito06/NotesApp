package com.example.mynotes

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import androidx.compose.runtime.State
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.db.NoteDao
import com.example.mynotes.db.NotesEntity
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val notesDao: NoteDao) :ViewModel() {

    val allNotes: Flow<List<NotesEntity>> = notesDao.getAllNotes()
    val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 123


    private val _permissionResult = MutableLiveData<Boolean>()
    val permissionResult: LiveData<Boolean> = _permissionResult


    fun insert(note:NotesEntity) = viewModelScope.launch{
        notesDao.insert(note)
    }

    fun update(note: NotesEntity) = viewModelScope.launch {
        notesDao.update(note)
    }

    fun delete(note: NotesEntity) = viewModelScope.launch {
        notesDao.deleteNote(note)
    }

    // Function to export data to PDF
    fun exportToPDF(context: Context, dataListState: State<List<NotesEntity>>) {
        val entities = dataListState.value
        viewModelScope.launch {
            try {
                val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "data.pdf")
                val outputStream = FileOutputStream(file)
                entities.forEach { entity ->
                    outputStream.write("${entity.id}, ${entity.title}, ${entity.content}\n".toByteArray())
                }
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun hasWritePermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }


    fun requestWritePermission(activity: Activity, context: Context,dataListState: State<List<NotesEntity>> ) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            WRITE_EXTERNAL_STORAGE_REQUEST_CODE,

            )
    }

    fun onPermissionResult(grantResults: IntArray) {
        _permissionResult.value = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
    }
}