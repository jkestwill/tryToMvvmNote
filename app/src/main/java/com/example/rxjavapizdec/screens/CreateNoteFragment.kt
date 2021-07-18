package com.example.rxjavapizdec.screens

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.rxjavapizdec.R
import com.example.rxjavapizdec.models.Note
import com.example.rxjavapizdec.models.Styles
import com.example.rxjavapizdec.other.screenShot
import com.example.rxjavapizdec.other.screenShotToByteArray
import com.example.rxjavapizdec.viewmodels.MainViewModel
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : Fragment(R.layout.fragment_create_note) {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var title: EditText
    private lateinit var desc: EditText
    private lateinit var screenShotView:NestedScrollView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<ImageButton>(R.id.edit_fragment_back)
        title = view.findViewById(R.id.title)
        desc = view.findViewById(R.id.desc)
        screenShotView=view.findViewById(R.id.fragment_main_nested_scroll_view)

        val currentDate = SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.getDefault()
        )
            .format(Date())

        button.setOnClickListener {
            viewModel.insert(
                Note(
                    title.text.toString(),
                    desc.text.toString(),
                   screenShotToByteArray(screenShot(screenShotView)) ,
                    Styles(
                        1,
                        false
                    ),
                    currentDate
                )

            )
            findNavController().popBackStack()
        }
    }


}