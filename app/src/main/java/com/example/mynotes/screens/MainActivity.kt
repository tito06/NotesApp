package com.example.mynotes.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.NavScreen
import com.example.mynotes.NoteViewModel
import com.example.mynotes.db.NoteDao
import com.example.mynotes.db.NotesDb
import com.example.mynotes.ui.theme.MyNotesTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val noteViewModel:NoteViewModel by viewModels()

   // @Inject lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     //   appDatabase = NotesDb.getDatabase(applicationContext)

      //  noteViewModel = NoteViewModel(appDatabase.notesDao())


        setContent {
            MyNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    Start(noteViewModel = noteViewModel)
                }
            }
        }
    }
}

@Composable
fun Start(noteViewModel: NoteViewModel) {


    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.NoteListScreen.route ){
        composable(route = NavScreen.NoteListScreen.route){
            NoteListScreen(navController = navController,
                noteViewModel = noteViewModel)
        }
        
        composable(route= NavScreen.AddNoteScreen.route){

            AddNoteScreen(navController = navController,
                noteViewModel = noteViewModel
            )
        }
    }
}

