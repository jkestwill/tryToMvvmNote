package com.example.rxjavapizdec.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.rxjavapizdec.NoteApp
import com.example.rxjavapizdec.R
import com.example.rxjavapizdec.models.Note
import com.example.rxjavapizdec.viewmodels.MainActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EditFragment : Fragment(R.layout.fragment_edit) {
private lateinit var notes:MutableList<Note>
private lateinit var viewModel:MainActivityViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button=view.findViewById<FloatingActionButton>(R.id.crete_new_note)
        button.setOnClickListener{
            findNavController().navigate(R.id.action_editFragment2_to_mainFragment)
        }
        viewModel= MainActivityViewModel()
        viewModel.noteRepository.fetchNotes((requireActivity().application as NoteApp).database)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.noteRepository.disposableBag.clear()
    }


}