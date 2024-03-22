package com.example.mynotes.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.NavScreen
import com.example.mynotes.NoteViewModel
import com.example.mynotes.db.NotesEntity

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteListScreen(noteViewModel: NoteViewModel) {

    val allNotes: State<List<NotesEntity>> =
        noteViewModel.allNotes.collectAsState(initial = emptyList())

    val navController = rememberNavController()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(NavScreen.AddNoteScreen.route)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")

            }
        }
    ) {
        LazyColumn{
          items(allNotes.value){note ->

              Text(text = note.title)
              Spacer(modifier = Modifier.height(10.0.dp))
              Text(text = note.content)

          }
        }
    }
}