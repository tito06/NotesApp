package com.example.mynotes

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.db.NoteDao
import com.example.mynotes.db.NotesEntity
import com.google.gson.Gson
import com.itextpdf.text.Document
import com.itextpdf.text.PageSize
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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
    fun generatePdf(file: File, data: State<List<NotesEntity>>, context: Context) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val canvas = page.canvas
        val paint = Paint()
        //paint.color = Color.

        val lineHeight = 25 // Adjust as needed
        var yPosition = 25F


        // Draw text on the PDF page
        data.value.forEach { entity ->

            canvas.drawText(entity.id.toString() +"."+"  "+ entity.title , 10F, yPosition, paint)
            yPosition += lineHeight
            canvas.drawText("      "+ entity.content, 10F, yPosition, paint)
            yPosition += lineHeight

        }
        pdfDocument.finishPage(page)

        // Save the PDF document

        val outputStream = FileOutputStream(file)
        pdfDocument.writeTo(outputStream)

        pdfDocument.close()
        Toast.makeText(context, "Data saved publicly..", Toast.LENGTH_SHORT).show()


    }



    fun hasWritePermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun generateFileName(type:String): String {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        if (type == "pdf") {
            return "download_$timeStamp.pdf"
        } else {
            return "download_$timeStamp.txt"
        }// You can adjust the file extension as per your requirements
    }

    // write text
     fun writeTextData(file: File, data: State<List<NotesEntity>>, context: Context) {
        var fileOutputStream: FileOutputStream? = null
        val gson = Gson()
        val jsonString = gson.toJson(data)

        try {
            fileOutputStream = FileOutputStream(file)
            //fileOutputStream.write(jsonString.toByteArray())

            data.value.forEach { entity ->
                fileOutputStream.write("${entity.id}, ${entity.title}, ${entity.content}\n".toByteArray())
            }
            Toast.makeText(context, "Data saved publicly..", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }



}