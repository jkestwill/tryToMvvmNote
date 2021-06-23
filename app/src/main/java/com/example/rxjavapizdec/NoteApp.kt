package com.example.rxjavapizdec

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import com.example.rxjavapizdec.repository.database.NoteDatabase

class NoteApp:Application() {
 lateinit var database:NoteDatabase
    override fun onCreate() {
        super.onCreate()

        database= Room

            .databaseBuilder(this,NoteDatabase::class.java,"notes")
            .fallbackToDestructiveMigration()
            .build()
    }
}