package com.example.noteApp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.noteApp.NoteApp
import com.example.noteApp.models.Note
import com.example.noteApp.repository.NoteRepository
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
                Log.e("CALL","call")
            }, {
                Log.e("eerr", it.message.toString())
            })

    }

    fun search(text: String) {

        Observable.fromArray(mutableLiveData.value)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .map {  it!!.filter {  it.title.lowercase().contains(text) || it.description.lowercase().contains(text) } }
            .subscribe( {
                searchedNotes.postValue(it)
            },{
                Log.e("ErrorRX",it.message.toString())
            })

    }




}