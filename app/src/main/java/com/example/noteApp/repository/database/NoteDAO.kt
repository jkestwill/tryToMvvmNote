package com.example.noteApp.repository.database

import androidx.room.*
import com.example.noteApp.models.Note
import io.reactivex.rxjava3.core.*

@Dao
interface NoteDAO {
    @Query("SELECT * FROM note")
    fun getAll(): Single<List<Note>>

    @Delete
    fun delete( note:Note):Single<Int>

    @Update
    fun update( note: Note):Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note:Note): Maybe<Long>

    @Query("SELECT * FROM note ORDER BY title ASC")
    fun sortedNotesByTitleAsc():Single<List<Note>>

    @Query("SELECT * FROM note ORDER BY title DESC")
    fun sortedNotesByTitleDesc():Single<List<Note>>

    @Query("SELECT * FROM note ORDER BY datetime(date) ASC")
    fun sortedNoteByDateAsc():Single<List<Note>>

    @Query("SELECT * FROM note ORDER BY datetime(date) DESC")
    fun sortedNoteByDateDesc():Single<List<Note>>
}