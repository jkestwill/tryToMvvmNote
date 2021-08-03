package com.example.noteApp.repository

import com.example.noteApp.models.Note
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface Repository {
    fun getAllNotes(): Single<List<Note>>

    fun insert(note:Note): Maybe<Long>

    fun delete(note:Note): Single<Int>

    fun update(note:Note):Single<Int>

    fun sortedNotesByTitleDesc():Single<List<Note>>

    fun sortedNotesByTitleAsc():Single<List<Note>>

    fun sortedNoteByDateAsc():Single<List<Note>>

    fun sortedNoteByDateDesc():Single<List<Note>>
}