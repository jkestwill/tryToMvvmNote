package com.example.rxjavapizdec.viewmodels

import androidx.lifecycle.ViewModel
import com.example.rxjavapizdec.repository.NoteRepository

class MainActivityViewModel: ViewModel() {
    val noteRepository=NoteRepository()


}