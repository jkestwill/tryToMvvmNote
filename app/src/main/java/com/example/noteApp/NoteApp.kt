package com.example.noteApp

import android.app.Application
import androidx.room.Room
import com.example.noteApp.repository.database.NoteDatabase

class NoteApp:Application() {
    @Volatile
 lateinit var database:NoteDatabase
    override fun onCreate() {
        super.onCreate()

        database= Room
            .databaseBuilder(this.applicationContext,NoteDatabase::class.java,"notes")
            .fallbackToDestructiveMigration()
            .build()
    }
}