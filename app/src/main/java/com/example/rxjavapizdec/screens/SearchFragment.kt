package com.example.rxjavapizdec.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjavapizdec.R
import com.example.rxjavapizdec.adapters.MainFragmentRecViewAdapter
import com.example.rxjavapizdec.viewmodels.SearchViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import java.util.concurrent.TimeUnit


class SearchFragment : Fragment(R.layout.fragment_search),
    MainFragmentRecViewAdapter.OnItemClickListener {

    private val searchViewModel: SearchViewModel by activityViewModels()
    private lateinit var searchField: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: MainFragmentRecViewAdapter
    private lateinit var emptyTv: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initRecyclerView()
        Observable.create(ObservableOnSubscribe<String> { subscirber ->
            searchField.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    subscirber.onNext(s.toString())

                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        })
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { it.trim() }
            .subscribe {
                Log.e("Search", it.toString())
                searchViewModel.search(it)
            }
        searchViewModel.searchedNotes.observe(viewLifecycleOwner) {
            if (searchField.text.toString().isNotEmpty()) {
                emptyTv.visibility = View.INVISIBLE
                recyclerView.visibility = View.VISIBLE
                Log.e("Empty", it.isNotEmpty().toString())

            } else {
                emptyTv.visibility = View.VISIBLE
                recyclerView.visibility = View.INVISIBLE
            }
            recyclerAdapter.update(it)
        }


    }

    private fun initRecyclerView() {
        recyclerView = requireView().findViewById(R.id.search_recycler_view)
        recyclerAdapter = MainFragmentRecViewAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            true
        )
        recyclerView.adapter = recyclerAdapter
    }

    override fun onStart() {
        super.onStart()
        searchViewModel.getNotes()
    }

    private fun initViews() {
        searchField = requireView().findViewById(R.id.search_field)
        emptyTv = requireView().findViewById(R.id.not_found_search)
    }

    override fun onClick(pos: Int) {
        val bundle = Bundle()
        bundle.putSerializable("note",recyclerAdapter.getItemAt(pos))
        findNavController().navigate(R.id.action_searchFragment_to_editNoteFragment,bundle)
    }
}