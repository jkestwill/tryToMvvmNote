package com.example.rxjavapizdec.repository

import com.example.rxjavapizdec.models.Note
import com.example.rxjavapizdec.repository.database.NoteDatabase
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable

interface Repository {
    fun getAllNotes(): Single<List<Note>>

    fun insert(note:Note): Maybe<Long>

    fun delete(note:Note): Single<Int>

    fun update(note:Note):Single<Int>
}