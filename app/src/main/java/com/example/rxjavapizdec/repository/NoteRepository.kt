package com.example.rxjavapizdec.repository

import android.util.Log
import com.example.rxjavapizdec.models.Note
import com.example.rxjavapizdec.repository.database.NoteDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NoteRepository {
    private val TAG="NodeRepository"
     val disposableBag=CompositeDisposable()
    fun fetchNotes(db:NoteDatabase){

        disposableBag.add( db.noteDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
             Log.e(TAG,it.toString())
            },
                {
                    Log.e(TAG,it.message.toString())
                },
                {
                    Log.e(TAG,"Compelte")
                }
            )
        )
    }

    fun insert(note:Note,db: NoteDatabase){

       disposableBag.add( db.noteDao().insert(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(TAG,"Insert Complete")
            },{
                Log.e(TAG,it.message.toString())
            })
       )
    }

    fun delete(note:Note,db:NoteDatabase){
      disposableBag.add(  db.noteDao().delete(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(TAG,"Delete Complete $it")
            },
                {
                    Log.e(TAG,it.message.toString())
                })
      )
    }

    fun update(note:Note,db: NoteDatabase){
      disposableBag.add(  db.noteDao().update(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(TAG,"Update Complete $it")
            },{
                Log.e(TAG,it.message.toString())
            })
      )
    }
}