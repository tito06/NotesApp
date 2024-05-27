package com.example.mynotes.screens

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.NavScreen
import com.example.mynotes.ViewModel.NoteViewModel
import com.example.mynotes.ui.theme.MyNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val noteViewModel: NoteViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()




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

@RequiresApi(Build.VERSION_CODES.O)
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

        composable(NavScreen.NoteDetailScreen.route + "/{title}" + "/{content}" + "/{id}"){
            val title = it.arguments?.getString("title")
            val content = it.arguments?.getString("content")
            val id = it.arguments?.getString("id")
            if(id!=null){
                NoteDetailScreen(
                    navController = navController,
                    noteViewModel = noteViewModel,
                    title = title,
                    content = content,
                    id = id.toLong()
                )
            }
        }



        composable(NavScreen.UpdateScreen.route + "/{title}" + "/{content}" + "/{id}"){
            val title = it.arguments?.getString("title")
            val content = it.arguments?.getString("content")
            val id = it.arguments?.getString("id")
            if (id != null) {
                UpdateNoteScreen(navController = navController,
                    noteViewModel =noteViewModel,
                    title =title,
                    content =content ,
                    id = id.toLong())
            }
        }
    }
}

