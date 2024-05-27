package com.example.mynotes.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mynotes.NavScreen
import com.example.mynotes.ViewModel.NoteViewModel
import com.example.mynotes.db.NotesEntity
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdateNoteScreen(
    navController: NavController,
    noteViewModel: NoteViewModel,
    title:String?,
    content:String?,
    id:Long

) {


    var noteTitle by remember { mutableStateOf(title) }
    var noteContent by remember { mutableStateOf(content) }
    var date = LocalDate.now()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = noteTitle!!,
            onValueChange = { noteTitle = it },
            label = { Text(text = "Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = noteContent!!,
            onValueChange = { noteContent = it },
            label = { Text(text = "Content") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val updatedNote = NotesEntity(id= id,title = noteTitle!!, content = noteContent!!,
                    date, category = "")
                noteViewModel.update(updatedNote)
                navController.navigate(NavScreen.NoteListScreen.route)
               // navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Update")
        }
    }
}