package com.example.rxjavapizdec.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.rxjavapizdec.NoteApp
import com.example.rxjavapizdec.R
import com.example.rxjavapizdec.models.Note
import com.example.rxjavapizdec.models.Styles
import com.example.rxjavapizdec.viewmodels.MainActivityViewModel

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var title:EditText
    private lateinit var desc:EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button=view.findViewById<Button>(R.id.edit_fragment_back)
        title=view.findViewById(R.id.title)
        desc=view.findViewById(R.id.desc)
        viewModel=MainActivityViewModel()
        button.setOnClickListener{
            viewModel.noteRepository.insert(
                Note(
                title.text.toString(),
                    desc.text.toString(),
                    "jaja",
                    Styles(
                        1,
                        false
                    )
            ),
                (requireActivity().application as NoteApp).database
            )
           findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.noteRepository.disposableBag.clear()
    }


}