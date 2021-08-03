package com.example.noteApp.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.noteApp.R
import com.example.noteApp.models.Note
import com.example.noteApp.other.screenShot
import com.example.noteApp.other.screenShotToByteArray
import com.example.noteApp.viewmodels.MainViewModel


class EditNoteFragment : Fragment(R.layout.fragment_edit_note) {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var title: EditText
    private lateinit var desc: EditText
    private lateinit var buttonBack: ImageButton
    private lateinit var editableNote: Note


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editableNote = requireArguments().getSerializable("note") as Note
        initViews()
        title.setText(editableNote.title)
        desc.setText(editableNote.description)
        desc.text


        buttonBack.setOnClickListener {
            if (title.text.toString() != editableNote.title ||
                desc.text.toString() != editableNote.description
            ) {
                saveChanges()
                Log.e("Changed", "Changed")
            }

            findNavController().popBackStack()
        }



        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            if (title.text.toString() != editableNote.title ||
                desc.text.toString() != editableNote.description
            ) {
                saveChanges()
                Log.e("Changed", "Changed")
            }
            findNavController().popBackStack()
        }


    }


    private fun saveChanges() {
        if (title.text.isNotBlank() || title.text.isNotBlank()) {
            editableNote.title = title.text.toString()
            editableNote.description = desc.text.toString()
            editableNote.thumbnail = screenShotToByteArray(screenShot(desc))
            viewModel.update(editableNote)
        } else {
            viewModel.delete(editableNote)
            Toast.makeText(context, "Nothing to save. Note discarded", Toast.LENGTH_LONG).show()
        }
    }

    private fun initViews() {
        title = requireView().findViewById(R.id.title)
        desc = requireView().findViewById(R.id.edit_desc)
        buttonBack = requireView().findViewById(R.id.edit_fragment_back)


    }


}