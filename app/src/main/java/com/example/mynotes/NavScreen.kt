package com.example.mynotes

sealed class NavScreen(val route:String) {

    object AddNoteScreen:NavScreen("addnote")
    object NoteListScreen:NavScreen("notelist")
}