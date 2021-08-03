package com.example.noteApp.repository.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.noteApp.models.Note
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest : TestCase() {
    private lateinit var db: NoteDatabase
    private lateinit var noteDAO: NoteDAO
    private var disposable = CompositeDisposable()

    @Before
    public override fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java
        )
            .build()
        noteDAO = db.noteDao()
    }

    @After
    fun closeDb() {
        db.close()
        disposable.clear()
    }

    @Test
    fun writeAndReadDb() {
        val note = Note("Title", "test", byteArrayOf(), "12.05.1222")
        noteDAO.insert(note)
        disposable.add(
            noteDAO.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    assert(it.contains(note))
                }, {

                })
        )


    }

    @Test
    fun deleteNote() {
        val note = Note("Title", "test", byteArrayOf(), "12.05.1222")
        noteDAO.insert(note)
        val notes = mutableListOf<Note>()
        disposable.add(
            noteDAO.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    notes.addAll(it)
                }, {

                })
        )
        disposable.add(noteDAO.delete(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                assertEquals(1, it)
                assert(!notes.contains(note))
            }, {

            }
            )
        )
    }




}