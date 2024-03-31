package com.example.mynotes.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mynotes.NavScreen
import com.example.mynotes.NoteViewModel
import com.example.mynotes.db.NotesEntity
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteListScreen(
    navController: NavController,
    noteViewModel: NoteViewModel
) {




    val allNotes: State<List<NotesEntity>> =
        noteViewModel.allNotes.collectAsState(initial = emptyList())

   val context = LocalContext.current



    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(NavScreen.AddNoteScreen.route)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")

            }
        }
    ) {
        Column {
            IconButton(onClick = {
                if (noteViewModel.hasWritePermission(context)){
                    noteViewModel.exportToPDF(context,allNotes)
                }else {
                    noteViewModel.requestWritePermission(activity = Activity(),
                        context,allNotes)
                }


            }) {
                Text(text = "PDF")
            }
            LazyColumn{
                items(allNotes.value){note ->

                    Card(modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(4.dp, 4.dp)
                        .background(Color.White)
                    ) {

                        Row(modifier = Modifier
                            .padding(8.dp, 10.dp)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(text = note.title,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                    color = Color.Blue

                                )
                                Spacer(modifier = Modifier.height(10.0.dp))
                                Text(text = note.content,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Magenta)
                            }

                            Row {
                                IconButton(onClick = {
                                    navController.navigate("${NavScreen.UpdateScreen.route}/${note.title}/${note.content}/${note.id}")
                                }) {
                                    Icon(imageVector = Icons.Default.Edit,
                                        contentDescription ="Edit" )

                                }


                                IconButton(onClick = {
                                    noteViewModel.delete(note)
                                }) {

                                    Icon(imageVector = Icons.Default.Delete,
                                        contentDescription ="Delete",
                                        tint = Color.Red)
                                }
                            }



                        }
                    }




                }
            }

        }
    }
}