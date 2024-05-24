package com.example.mynotes

sealed class NavScreen(val route:String) {

    object AddNoteScreen:NavScreen("addnote")
    object NoteListScreen:NavScreen("notelist")

    object  NoteDetailScreen:NavScreen("notedetail")

    object UpdateScreen:NavScreen("updatescreen")
}