package com.example.rxjavapizdec.viewmodels

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rxjavapizdec.NoteApp
import com.example.rxjavapizdec.models.Note
import com.example.rxjavapizdec.repository.NoteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val noteRepository = NoteRepository((application as NoteApp).database)
    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }
    val noteList: MutableLiveData<List<Note>> = MutableLiveData<List<Note>>()

    companion object {
        const val TAG = "MainActivityViewModel"
    }




    // #################### db works ####################
    fun getNotes() {
        disposable.add(noteRepository
            .getAllNotes()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    noteList.postValue(it)
                },
                {
                    Log.e("Ya v ahue","sheeeeees")
                }
                )
        )

    }

    fun insert(note: Note) {
        disposable.add(
            noteRepository
                .insert(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e(TAG, "Insert Complete")
                }, {
                    Log.e(TAG, it.message.toString())
                })
        )
    }

    fun delete(note: Note) {
        disposable.add(
            noteRepository
                .delete(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e(TAG, "Delete Complete $it")
                },
                    {
                        Log.e(TAG, it.message.toString())
                    })
        )
    }

    fun update(note: Note) {
        disposable.add(
            noteRepository
                .update(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e(TAG, "Update Complete $it")
                }, {
                    Log.e(TAG, it.message.toString())
                })
        )
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}