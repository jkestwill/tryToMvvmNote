package com.example.rxjavapizdec.repository

import android.util.DisplayMetrics
import android.util.Log
import com.example.rxjavapizdec.models.Note
import com.example.rxjavapizdec.repository.database.NoteDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NoteRepository(
    @Volatile
    private var db: NoteDatabase
    )
    :Repository {


    lateinit var noteData: Single<List<Note>>
    private val TAG="NodeRepository"


   override fun getAllNotes(): Flowable<List<Note>> =db.noteDao().getAll()


    override fun insert(note:Note): Maybe<Long> = db.noteDao().insert(note)



    override fun delete(note:Note):Single<Int>{
     return   db.noteDao().delete(note)


    }

  override fun update(note:Note):Single<Int>{
    return   db.noteDao().update(note)


    }
}