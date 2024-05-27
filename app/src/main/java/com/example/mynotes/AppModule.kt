package com.example.mynotes

import android.app.Application
import androidx.room.Room
import com.example.mynotes.ViewModel.NoteViewModel
import com.example.mynotes.db.NotesDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideDb(app:Application):NotesDb{
        return  Room.databaseBuilder(
            app,
            NotesDb::class.java,
            "notes"
        ).addMigrations(Migration_1_2,Migration_2_3)
            .build()
    }   


    @Provides
    @Singleton
    fun provideVm(notedb:NotesDb): NoteViewModel {
        return NoteViewModel(notedb.notesDao())
    }


}
