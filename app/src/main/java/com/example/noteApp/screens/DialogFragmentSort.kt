package com.example.noteApp.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import com.example.noteApp.R
import com.example.noteApp.other.OrderParameters
import com.example.noteApp.other.SortParameters
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogFragmentSort(private val df:DialogFragmentSortEvents) : BottomSheetDialogFragment() {
    private lateinit var buttonDone: Button
    private lateinit var buttonCancel: Button
    private lateinit var rgSortBy:RadioGroup
    private lateinit var rgOrder:RadioGroup
    private lateinit var sortParameters: SortParameters
    private lateinit var orderParameters: OrderParameters
    private val TAG=DialogFragmentSort::class.java.name

    interface DialogFragmentSortEvents{
        fun onSortParametresSelected(orderParameters: OrderParameters, sortParameters: SortParameters)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()


        buttonDone.setOnClickListener{

            when( rgOrder.checkedRadioButtonId){
                R.id.ascending_rb->{
                    orderParameters=OrderParameters.ASCENDING
                }
                R.id.descending_rb->{
                    orderParameters=OrderParameters.DESCENDING
                }
            }

            when(rgSortBy.checkedRadioButtonId){
                R.id.title_rb->{
                    sortParameters=SortParameters.TITlE
                }
                R.id.date_created_rb->{
                    sortParameters=SortParameters.DATE_CREATED
                }
            }

            df.onSortParametresSelected(orderParameters,sortParameters)
            dismiss()
        }

        buttonCancel.setOnClickListener {
            dismiss()
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_fragment_sort, container, false)

    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    private fun initViews(){
        buttonCancel=requireView().findViewById(R.id.filter_button_no)
        buttonDone=requireView().findViewById(R.id.filter_button_yes)
        rgOrder=requireView().findViewById(R.id.rg_order)
        rgSortBy=requireView().findViewById(R.id.rg_sort_by)

    }

}