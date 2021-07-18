package com.example.rxjavapizdec.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjavapizdec.R
import com.example.rxjavapizdec.adapters.MainFragmentRecViewAdapter
import com.example.rxjavapizdec.other.OnItemSwap
import com.example.rxjavapizdec.viewmodels.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment(R.layout.fragment_main),
    MainFragmentRecViewAdapter.OnItemClickListener {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: MainFragmentRecViewAdapter
    private lateinit var searchButton:ImageButton
    private lateinit var createNoteButton:FloatingActionButton
    private val TAG = "EditFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViewAdapter()
       initViews()
        createNoteButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_createNoteFragment)
        }
        searchButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }

        viewModel.getNotes()
        viewModel.noteList.observe(viewLifecycleOwner, {
            recyclerViewAdapter.update(it)
        })


    }


    private fun initRecyclerViewAdapter() {
        recyclerView = requireView().findViewById(R.id.fragment_main_recycler_view)
        recyclerViewAdapter = MainFragmentRecViewAdapter(this)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            true
        )
        val swipeHandler = object : OnItemSwap(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //remove items
                viewModel.delete(recyclerViewAdapter.getItemAt(viewHolder.adapterPosition))
                recyclerViewAdapter.removeAt(viewHolder.adapterPosition)

            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }
    private fun initViews(){
        createNoteButton=requireView().findViewById(R.id.crete_new_note)
        searchButton=requireView().findViewById(R.id.fragment_main_search)
    }

    override fun onClick(pos: Int) {

       //send note data from recycler view item to EditNoteFragment  and open EditNoteFragment
        val note = recyclerViewAdapter.getItemAt(pos)
        val bundle = Bundle()
        bundle.putSerializable("note", note)
        findNavController().navigate(R.id.action_mainFragment_to_editNoteFragment, bundle)
    }


}