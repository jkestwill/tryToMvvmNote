package com.example.noteApp.screens

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.noteApp.R
import com.example.noteApp.models.Note

import com.example.noteApp.other.screenShot
import com.example.noteApp.other.screenShotToByteArray
import com.example.noteApp.repository.database.DateConverters
import com.example.noteApp.viewmodels.MainViewModel
import java.time.OffsetDateTime


class CreateNoteFragment : Fragment(R.layout.fragment_create_note) {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var title: EditText
    private lateinit var desc: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<ImageButton>(R.id.edit_fragment_back)
        title = view.findViewById(R.id.title)
        desc = view.findViewById(R.id.desc)



        button.setOnClickListener {
            if(title.text.isNotBlank() || desc.text.isNotBlank()) {
                insertNoteInDB()
            }
            findNavController().popBackStack()
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner){
            if(title.text.isNotBlank() || desc.text.isNotBlank()){
                insertNoteInDB()
            }
            findNavController().popBackStack()
        }
    }

    private fun insertNoteInDB(){
        val date=OffsetDateTime.now()
        viewModel.insert(
            Note(
                title.text.toString(),
                desc.text.toString(),
                screenShotToByteArray(screenShot(desc)),
                DateConverters.fromOffsetDateTime(date)!!
            )

        )
    }


}