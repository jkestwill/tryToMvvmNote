package com.example.rxjavapizdec.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rxjavapizdec.models.Note

@Database(entities = [Note::class],version = 2)
abstract class NoteDatabase:RoomDatabase() {

    abstract fun noteDao():NoteDAO
}