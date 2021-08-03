package com.example.noteApp.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteApp.R
import com.example.noteApp.adapters.MainFragmentRecViewAdapter
import com.example.noteApp.adapters.OnItemSwap
import com.example.noteApp.other.OrderParameters
import com.example.noteApp.other.SortParameters
import com.example.noteApp.viewmodels.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


//*
// add opportunity to change font style and font size
// add unit tests
//
//
// *//


class MainFragment : Fragment(R.layout.fragment_main),
    MainFragmentRecViewAdapter.OnItemClickListener,
    DialogFragmentSort.DialogFragmentSortEvents {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: MainFragmentRecViewAdapter
    private lateinit var searchButton: ImageButton
    private lateinit var createNoteButton: FloatingActionButton
    private lateinit var sortButton: ImageButton


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViewAdapter()
        initViews()
        createNoteButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_createNoteFragment)
        }
        searchButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
        sortButton.setOnClickListener {
            DialogFragmentSort(this).show(childFragmentManager, "TAG")
        }


        viewModel.noteList.observe(viewLifecycleOwner, {
            recyclerViewAdapter.update(it)
        })


    }

    override fun onStart() {
        super.onStart()
        viewModel.getNotes()
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

    private fun initViews() {
        createNoteButton = requireView().findViewById(R.id.crete_new_note)
        searchButton = requireView().findViewById(R.id.fragment_main_search)
        sortButton = requireView().findViewById(R.id.fragment_main_sort)
    }

    override fun onClick(pos: Int) {

        //send note data from recycler view item to EditNoteFragment  and open EditNoteFragment
        val note = recyclerViewAdapter.getItemAt(pos)
        val bundle = Bundle()
        bundle.putSerializable("note", note)
        findNavController().navigate(R.id.action_mainFragment_to_editNoteFragment, bundle)
    }

    override fun onSortParametresSelected(
        orderParameters: OrderParameters,
        sortParameters: SortParameters
    ) {

        when (sortParameters) {
            SortParameters.TITlE -> {
                when (orderParameters) {
                    OrderParameters.ASCENDING -> viewModel.sortByTitleAsc()

                    OrderParameters.DESCENDING -> viewModel.sortByTitleDesc()
                }

            }
            SortParameters.DATE_CREATED -> {
                when (orderParameters) {
                    OrderParameters.ASCENDING -> viewModel.getDateSortByAsc()

                    OrderParameters.DESCENDING -> viewModel.getDateSortByDesc()
                }
            }
        }

    }


}