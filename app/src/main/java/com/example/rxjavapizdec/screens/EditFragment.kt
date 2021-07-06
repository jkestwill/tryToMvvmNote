package com.example.rxjavapizdec.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjavapizdec.NoteApp
import com.example.rxjavapizdec.R
import com.example.rxjavapizdec.adapters.MainFragmentRecViewAdapter
import com.example.rxjavapizdec.models.Note
import com.example.rxjavapizdec.viewmodels.MainActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class EditFragment : Fragment(R.layout.fragment_edit) {

private val viewModel:MainActivityViewModel by activityViewModels()
private lateinit var recyclerView:RecyclerView
private lateinit var recyclerViewAdapter:MainFragmentRecViewAdapter

private val TAG="EditFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViewAdapter()
        val button=view.findViewById<FloatingActionButton>(R.id.crete_new_note)
        button.setOnClickListener{
            findNavController().navigate(R.id.action_editFragment2_to_mainFragment)
        }

        viewModel.getNotes()
        viewModel.noteList.observe(viewLifecycleOwner,{
            recyclerViewAdapter.update(it)
        })

    }




    private fun initRecyclerViewAdapter(){
        recyclerView=requireView().findViewById(R.id.fragment_main_recycler_view)
        recyclerViewAdapter= MainFragmentRecViewAdapter()
        recyclerView.adapter=recyclerViewAdapter
        recyclerView.layoutManager=LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            true)


    }




}