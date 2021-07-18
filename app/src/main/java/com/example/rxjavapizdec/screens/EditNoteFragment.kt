package com.example.rxjavapizdec.screens

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.rxjavapizdec.R
import com.example.rxjavapizdec.models.Note
import com.example.rxjavapizdec.viewmodels.MainViewModel

//editNote
class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var title: EditText
    private lateinit var desc: EditText
    private lateinit var buttonBack: ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editableNote = requireArguments().getSerializable("note") as Note
        initViews()

        title.setText(editableNote.title)
        desc.setText(editableNote.description)

        buttonBack.setOnClickListener{
            editableNote.title=title.text.toString()
            editableNote.description=desc.text.toString()
            viewModel.update(editableNote)
            findNavController().popBackStack()
        }


    }

    private fun initViews() {
        title = requireView().findViewById(R.id.title)
        desc = requireView().findViewById(R.id.edit_desc)
        buttonBack = requireView().findViewById(R.id.edit_fragment_back)
    }


}