package com.example.mynotes.screens

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.mynotes.NavScreen
import com.example.mynotes.ViewModel.NoteViewModel
import com.example.mynotes.db.NotesEntity
import com.example.mynotes.popinFamily
import com.example.mynotes.popinFamilyTitle
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddNoteScreen(
    navController: NavController,
    noteViewModel: NoteViewModel
) {



    var showAllMenu by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val items  = listOf("All","Personal", "Work", "Grocery", "Shopping")
    var selectedItem by remember {
        mutableStateOf(items[0])
    }


    Scaffold(
        topBar = {
            TopAppBar(title = { androidx.compose.material3.Text(text = "New note",
                fontFamily = popinFamilyTitle)})
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 80.dp, 16.dp, 0.dp)
        ) {
            OutlinedTextField(
                value = noteViewModel.addnoteTitleNew,
                onValueChange = { noteViewModel.addnoteTitleNew = it},
                label = { Text(text = "Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))


            Card(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(width = 1.dp, Color.Black)
            ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    selectedItem,
                    fontFamily = popinFamily,
                    modifier = Modifier.padding(14.dp, 0.dp)
                )

                IconButton(
                    onClick = {
                        showAllMenu = !showAllMenu
                    }, modifier = Modifier.onGloballyPositioned {

                    },
                    colors = IconButtonDefaults.iconButtonColors(Color.Red)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Down arrow"
                    )
                }
            }
                    // Show the DropdownMenu
                    if (showAllMenu) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.TopEnd)
                        ) {
                            DropdownMenu(
                                expanded = showAllMenu,
                                onDismissRequest = { showAllMenu = false },
                                /*  offset = DpOffset(
                                x = with(LocalDensity.current) { buttonPosition.x.toDp() },
                                y = with(LocalDensity.current) { (buttonPosition.y).toDp() }
                            )*/


                            ) {
                                items.forEach {
                                    DropdownMenuItem(
                                        text = { androidx.compose.material3.Text(text = it) },
                                        onClick = {
                                            selectedItem = it
                                            showAllMenu = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }


            Spacer(modifier = Modifier.height(8.dp))


            Card(modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(width = 1.dp, Color.Black)
            ) {

                TextField(
                    value = noteViewModel.noteContent,
                    onValueChange = { noteViewModel.noteContent = it },
                    label = { Text(text = "Content") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )

            }





            Spacer(modifier = Modifier.height(16.dp))



            Button(
                onClick = {
                    if (noteViewModel.addnoteTitleNew.isNotEmpty() && noteViewModel.noteContent.isNotEmpty()) {
                        val newNote =
                            NotesEntity(title = noteViewModel.addnoteTitleNew.toString(), content = noteViewModel.noteContent, date = noteViewModel.date, category = selectedItem)
                        noteViewModel.insert(newNote)
                        navController.navigate(NavScreen.NoteListScreen.route)
                    }else{
                        Toast.makeText(context , "Can not save a blank file.", Toast.LENGTH_SHORT).show()
                    }

                    // navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Save")
            }
        }
    }


}