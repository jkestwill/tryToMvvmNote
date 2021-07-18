package com.example.rxjavapizdec.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.rxjavapizdec.NoteApp
import com.example.rxjavapizdec.models.Note
import com.example.rxjavapizdec.repository.NoteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val noteRepository = NoteRepository((application as NoteApp).database)
    private val mutableLiveData = MutableLiveData<List<Note>>()
    val searchedNotes = MutableLiveData<List<Note>>()


    fun getNotes() {
        noteRepository.getAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutableLiveData.postValue(it)
            }, {
                Log.e("eerr", it.message.toString())
            })

    }

    fun search(text: String) {
        val searchedItems = mutableListOf<Note>()
        Observable.fromIterable(mutableLiveData.value)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .filter {
                it.title.lowercase().contains(text) || it.description.lowercase().contains(text)
            }
            .map { searchedItems.add(it) }
            .subscribe {
                searchedNotes.postValue(mutableListOf())
                searchedNotes.postValue(searchedItems)
            }
    }
}