package com.example.rxjavapizdec.repository.database

import androidx.room.*
import com.example.rxjavapizdec.models.Note
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


}