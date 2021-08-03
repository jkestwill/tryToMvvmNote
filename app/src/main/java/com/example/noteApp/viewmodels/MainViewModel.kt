package com.example.noteApp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteApp.NoteApp
import com.example.noteApp.models.Note
import com.example.noteApp.repository.NoteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val noteRepository = NoteRepository((application as NoteApp).database)
    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }
    private val _noteList: MutableLiveData<List<Note>> = MutableLiveData<List<Note>>()
    val noteList: LiveData<List<Note>>
        get() = _noteList
    val searchedNotes:MutableLiveData<List<Note>> = MutableLiveData()


     val TAG = "MainActivityViewModel"


    fun getNotes() {
        disposable.add(noteRepository
            .getAllNotes()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _noteList.postValue(it)
                },
                {
                    Log.e(TAG, it.message.toString())
                }
            )
        )

    }

    fun getDateSortByDesc() {
        disposable.add(noteRepository.sortedNoteByDateDesc()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                _noteList.postValue(t1)

            }
        )
    }


    fun getDateSortByAsc() {
        disposable.add(noteRepository.sortedNoteByDateAsc()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                _noteList.postValue(t1)

            }
        )
    }

    fun sortByTitleDesc() {
        disposable.add(noteRepository.sortedNotesByTitleDesc()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                _noteList.postValue(t1)
            }
        )
    }

    fun sortByTitleAsc() {
        disposable.add(noteRepository.sortedNotesByTitleAsc()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                _noteList.postValue(t1)
            }
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

    fun search(text: String) {

        Observable.fromArray(_noteList.value)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .map {  it!!.filter {  it.title.lowercase().contains(text) || it.description.lowercase().contains(text) } }
            .subscribe( {
                searchedNotes.postValue(it)
            },{
                Log.e("ErrorRX",it.message.toString())
            })

    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}