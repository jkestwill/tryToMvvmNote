package com.example.noteApp.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteApp.models.Note

@Database(entities = [Note::class],version = 10)
abstract class NoteDatabase:RoomDatabase() {

    abstract fun noteDao():NoteDAO
}