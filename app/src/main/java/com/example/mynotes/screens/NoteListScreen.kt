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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.mynotes.NavScreen
import com.example.mynotes.NoteViewModel
import com.example.mynotes.db.NotesEntity
import android.Manifest

import android.os.Environment
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.toSize

import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteListScreen(
    navController: NavController,
    noteViewModel: NoteViewModel
) {

    val config = LocalConfiguration.current
    val screenwith = config.screenWidthDp.dp



    val allNotes: State<List<NotesEntity>> =
        noteViewModel.allNotes.collectAsState(initial = emptyList())

    val context = LocalContext.current



    val folder: File =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)


    var showDropDownMenu by remember { mutableStateOf(false) }

    var showAllMenu by remember { mutableStateOf(false) }
    var buttonPosition by remember { mutableStateOf(Offset.Zero) }
    var buttonSize by remember { mutableStateOf(Size.Zero) }
    var toggle by remember { mutableStateOf(false) }

    //temp
    val itemsChoice = listOf("all", "work", "personal", "grocery", "shopping")



    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Your Notes") },

                actions = {
                    IconButton(onClick = { showDropDownMenu = true  }) {
                        Icon(Icons.Filled.MoreVert, null)

                    }

                    DropdownMenu(
                        showDropDownMenu, { showDropDownMenu = false }
                        // offset = DpOffset((-102).dp, (-64).dp),
                    ) {
                        DropdownMenuItem(text = { Text(text = "Export to pdf") }, leadingIcon = {
                            Icon(imageVector = Icons.Filled.ExitToApp, contentDescription ="Export" )
                        }, onClick = {
                            val fileName = noteViewModel.generateFileName("pdf")
                            val file = File(folder, fileName)
                            if (noteViewModel.hasWritePermission(context)){
                                noteViewModel.generatePdf(file,allNotes,context)

                            }else {

                                ActivityCompat.requestPermissions(
                                    context as Activity,
                                    arrayOf<String?>(Manifest.permission.READ_EXTERNAL_STORAGE),
                                    23
                                )

                                noteViewModel.generatePdf(file,allNotes,context)

                            }
                            showDropDownMenu = false
                        })
                        DropdownMenuItem(text = { Text(text = "Export to txt") }, leadingIcon = {
                            Icon(imageVector = Icons.Filled.ExitToApp, contentDescription ="Export" )
                        }, onClick = {
                            val fileName = noteViewModel.generateFileName("txt")
                            val file = File(folder, fileName)
                            if (noteViewModel.hasWritePermission(context)){
                                noteViewModel.generatePdf(file,allNotes,context)

                            }else {

                                ActivityCompat.requestPermissions(
                                    context as Activity,
                                    arrayOf<String?>(Manifest.permission.READ_EXTERNAL_STORAGE),
                                    23
                                )

                                noteViewModel.writeTextData(file,allNotes,context)

                            }
                            showDropDownMenu = false
                        })

                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(NavScreen.AddNoteScreen.route)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")

            }
        }
    ) {



        Column(modifier = Modifier
            .padding(10.dp)
            .padding(0.dp, 64.dp)) {

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {

                Card(modifier = Modifier
                    .height(50.dp)
                    .width(screenwith / 2)
                    .padding(4.dp, 4.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                           "Search".toString(),
                            modifier = Modifier.padding(14.dp, 0.dp)
                        )

                        IconButton(onClick = {
                            showAllMenu = !showAllMenu
                        }, modifier = Modifier.onGloballyPositioned {
                            buttonSize = it.size.toSize()
                            buttonPosition = it.positionInRoot()
                        }
                           ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Down arrow"
                            )
                        }
                        // Show the DropdownMenu
                        if (showAllMenu) {
                            DropdownMenu(
                               modifier = Modifier
                                   .align(alignment = Alignment.CenterVertically),
                                expanded = showAllMenu,
                                onDismissRequest = { showAllMenu = false },
                                offset = DpOffset(
                                    x = with(LocalDensity.current) { buttonPosition.x.toDp() },
                                    y = with(LocalDensity.current) { (buttonPosition.y ).toDp() }
                                )

                            ) {
                                DropdownMenuItem(text = { Text(text = "all") }, onClick = { /*TODO*/ })
                                DropdownMenuItem(text = { Text(text = "work") }, onClick = { /*TODO*/ })
                                DropdownMenuItem(text = { Text(text = "personal") }, onClick = { /*TODO*/ })
                                DropdownMenuItem(text = { Text(text = "grocery") }, onClick = { /*TODO*/ })
                                DropdownMenuItem(text = { Text(text = "shopping") }, onClick = { /*TODO*/ })
                            }
                        }
                    }




                }

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {



                    IconButton(onClick = {
                        toggle = true
                    }) {

                        Icon(imageVector = Icons.Default.Add,
                            contentDescription ="Test" )

                    }

                    IconButton(onClick = {toggle = false}) {

                        Icon(imageVector = Icons.Default.List,
                            contentDescription ="Test" )

                    }

                }
            }

            Spacer(modifier = Modifier.height(15.dp))

           LazyRow(modifier = Modifier
               .fillMaxWidth()
               .height(50.dp)) {
                items(itemsChoice){
                    
                    Card(modifier = Modifier
                        .width(100.dp)
                        .fillMaxHeight()
                        .padding(2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color.Black)
                        ) {
                        
                            Row(modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically) {
                                Text(text = it)
                            }
                    }
                }
           }

            Spacer(modifier = Modifier.height(15.dp))
            if (!toggle) {
                   LazyColumn(modifier = Modifier.background(Color.Transparent)){
                items(allNotes.value){note ->

                    Card(modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(4.dp, 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color.Black)
                    ) {

                        Row(modifier = Modifier
                            .padding(8.dp, 10.dp)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(note.title,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                    color = Color.Blue

                                )
                                Spacer(modifier = Modifier.height(10.0.dp))
                                Text(text = note.content.toString(),
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
            } else {
                LazyHorizontalStaggeredGrid(
                    rows = StaggeredGridCells.Fixed(2),
                ) {

                    items(allNotes.value) { note ->

                        Card(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .padding(4.dp, 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            border = BorderStroke(1.dp, Color.Black)
                        ) {

                            Column(
                                modifier = Modifier
                                    .padding(8.dp, 10.dp)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    note.title,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                    color = Color.Blue

                                )
                                Spacer(modifier = Modifier.height(10.0.dp))
                                Text(
                                    text = note.content.toString(),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Magenta
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    IconButton(onClick = {
                                        navController.navigate("${NavScreen.UpdateScreen.route}/${note.title}/${note.content}/${note.id}")
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit"
                                        )

                                    }


                                    IconButton(onClick = {
                                        noteViewModel.delete(note)
                                    }) {

                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }


                        }
                    }
                }

            }
                
            }


        }

        }







