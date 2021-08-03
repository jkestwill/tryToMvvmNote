package com.example.noteApp.repository

import com.example.noteApp.models.Note
import com.example.noteApp.repository.database.NoteDatabase
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

class NoteRepository(
    @Volatile
    private var db: NoteDatabase
) : Repository {

    override fun getAllNotes(): Single<List<Note>> = db.noteDao().getAll()


    override fun insert(note: Note): Maybe<Long> = db.noteDao().insert(note)


    override fun delete(note: Note): Single<Int> = db.noteDao().delete(note)


    override fun update(note: Note): Single<Int> = db.noteDao().update(note)


    override fun sortedNotesByTitleDesc(): Single<List<Note>> = db.noteDao().sortedNotesByTitleDesc()


    override fun sortedNotesByTitleAsc(): Single<List<Note>> = db.noteDao().sortedNotesByTitleAsc()


    override fun sortedNoteByDateAsc(): Single<List<Note>> = db.noteDao().sortedNoteByDateAsc()


    override fun sortedNoteByDateDesc(): Single<List<Note>> = db.noteDao().sortedNoteByDateDesc()


}