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
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.toSize
import com.example.mynotes.R
import com.example.mynotes.montserratFamily
import com.example.mynotes.popinFamily
import com.example.mynotes.popinFamilyNormal
import com.example.mynotes.popinFamilyTitle
import com.example.mynotes.popinFamilyhead

import java.io.File
import java.util.Random


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteListScreen(
    navController: NavController,
    noteViewModel: NoteViewModel
) {

    val config = LocalConfiguration.current
    val screenwith = config.screenWidthDp.dp

    val isDarkTheme = isSystemInDarkTheme()




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
            TopAppBar(title = { Text(text = "Your Notes",
                fontFamily = popinFamilyTitle
            ) },

                actions = {
                    IconButton(onClick = { showDropDownMenu = true  }) {
                        Icon(Icons.Filled.MoreVert, null)

                    }

                    DropdownMenu(
                        showDropDownMenu, { showDropDownMenu = false }
                        // offset = DpOffset((-102).dp, (-64).dp),
                    ) {
                        DropdownMenuItem(text = { Text(text = "Export to pdf",fontFamily = popinFamilyNormal) }, leadingIcon = {
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
                        DropdownMenuItem(text = { Text(text = "Export to txt", fontFamily = popinFamilyNormal
                        ) }, leadingIcon = {
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
            .fillMaxHeight()
            .padding(10.dp, 84.dp, 10.dp, 0.dp)) {

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
                            fontFamily = popinFamily,
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
                                DropdownMenuItem(text = { Text(text = "all", fontFamily = popinFamilyNormal) }, onClick = { /*TODO*/ })
                                DropdownMenuItem(text = { Text(text = "work", fontFamily = popinFamilyNormal) }, onClick = { /*TODO*/ })
                                DropdownMenuItem(text = { Text(text = "personal", fontFamily = popinFamilyNormal) }, onClick = { /*TODO*/ })
                                DropdownMenuItem(text = { Text(text = "grocery", fontFamily = popinFamilyNormal) }, onClick = { /*TODO*/ })
                                DropdownMenuItem(text = { Text(text = "shopping", fontFamily = popinFamilyNormal) }, onClick = { /*TODO*/ })
                            }
                        }
                    }




                }

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {



                    IconButton(onClick = {
                        toggle = true
                    }) {

                        Icon(
                            painterResource(id = R.drawable.grid),
                            modifier = Modifier.size(20.dp),
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
                    if(isDarkTheme) {
                        Card(
                            modifier = Modifier
                                .width(100.dp)
                                .fillMaxHeight()
                                .padding(2.dp),

                            border = BorderStroke(1.dp, Color.Black)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = it,
                                    fontFamily = popinFamilyNormal
                                )
                            }
                        }
                    } else {
                        Card(
                            modifier = Modifier
                                .width(100.dp)
                                .fillMaxHeight()
                                .padding(2.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            border = BorderStroke(1.dp, Color.Black)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = it,
                                    fontFamily = popinFamilyNormal
                                )
                            }
                        }

                    }
                }
           }

            Spacer(modifier = Modifier.height(15.dp))
            if (!toggle) {
                   LazyColumn(modifier = Modifier.fillMaxHeight()){
                items(allNotes.value){note ->

                    Card(modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(4.dp, 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = generateRandomColor()
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
                                    color = Color.Black,
                                    fontFamily = popinFamilyhead

                                )
                                Spacer(modifier = Modifier.height(10.0.dp))
                                Text(text = note.content,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black,
                                    fontFamily = popinFamilyNormal)
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
                                        tint = Color.Black)
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
                                .height(70.dp)
                                .width(170.dp)
                                .padding(4.dp, 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = generateRandomColor()
                            ),
                            border = BorderStroke(1.dp, Color.Black)
                        ) {



                            Column(
                                modifier = Modifier
                                    .padding(8.dp, 10.dp)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.Start
                            ) {

                                Column {
                                    Text(
                                        note.title,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp,
                                        color = Color.Black,
                                        fontFamily = popinFamilyhead

                                    )
                                    Spacer(modifier = Modifier.height(10.0.dp))
                                    Text(
                                        text = note.content.toString(),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.Black,
                                        fontFamily = popinFamilyNormal
                                    )


                                }


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
                                            tint = Color.Black
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

fun generateRandomColor(): Color {
    val random = Random()
    val red = random.nextInt(256)
    val green = random.nextInt(256)
    val blue = random.nextInt(256)
    val colors  = listOf(
        Color(0xFF7986CB), // Indigo
        Color(0xFF64B5F6), // Blue
        Color(0xFF4FC3F7), // Light Blue
        Color(0xFF4DD0E1), // Cyan
        Color(0xFF4DB6AC), // Teal
        Color(0xFF81C784), // Green
        Color(0xFFAED581), // Light Green
        Color(0xFFFF8A65), // Orange
        Color(0xFFD4E157), // Lime
        Color(0xFFFFD54F), // Amber
        Color(0xFFFFB74D), // Deep Orange
        Color(0xFFA1887F), // Brown
        Color(0xFFE0E0E0), // Gray
        Color(0xFF90A4AE)  // Blue Gray

    )



    return colors[random.nextInt(colors.size)]
}







