package com.example.mynotes.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mynotes.NavScreen
import com.example.mynotes.ViewModel.NoteViewModel
import com.example.mynotes.popinFamilyTitle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    navController: NavController,
    noteViewModel: NoteViewModel,
    title:String?,
    content:String?,
    id:Long
){

    val noteTitle by remember { mutableStateOf(title) }
    val noteDetail by remember { mutableStateOf(content) }


    val config = LocalConfiguration.current
    val screenwith = config.screenWidthDp.dp

 Scaffold(
     topBar = {
         TopAppBar(title = { Text(text = title.toString(),
             fontFamily = popinFamilyTitle)},
         )
     },
 ) {

     Column(
         modifier = Modifier
             .fillMaxWidth()
             .fillMaxHeight()
             .padding(10.dp, 87.dp, 10.dp, 0.dp)
     ) {

         Spacer(modifier = Modifier.height(8.dp))
         Card(
             modifier = Modifier
                 .fillMaxWidth()
                 .height(screenwith)
                 .padding(4.dp, 14.dp, 4.dp, 4.dp),
             colors = CardDefaults.cardColors(
                 containerColor = Color.White
             ),
             border = BorderStroke(1.dp, Color.Black)
         ) {
             Column(modifier = Modifier.fillMaxHeight()
                 .fillMaxWidth()
                 .padding(10.dp,5.dp,2.dp,0.dp),
                 verticalArrangement = Arrangement.Top,
                 horizontalAlignment = Alignment.Start) {

                 Text(text = noteDetail.toString())

             }

         }



         Spacer(modifier = Modifier.height(16.dp))

         Button(
             onClick = {
                 navController.navigate("${NavScreen.UpdateScreen.route}/${title}/${content}/${id}")

             },
             modifier = Modifier.align(Alignment.End)
         ) {
             Text(text = "Update")
         }
     }
 }


}

