package com.example.noteApp.customViews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.noteApp.R
import com.google.android.material.appbar.AppBarLayout
import java.text.AttributedString
import java.util.ArrayList

class TextViewBehavior(private val context: Context) :
    CoordinatorLayout.Behavior<TextView>() {

    private  var smallTextView:TextView?=null

    constructor(context: Context, attr: AttributeSet?) : this(context) {


    }


    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: TextView,
        dependency: View
    ): Boolean {

        return dependency is Toolbar
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: TextView,
        dependency: View
    ): Boolean {
        if(smallTextView==null) {
            smallTextView = dependency.findViewById(R.id.small_text_view)
            Log.e("hot","ss")
        }

            smallTextView?.alpha=dependency.y/-578
            child.alpha = child.y/100


        return true
    }


}