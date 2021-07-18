package com.example.rxjavapizdec.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjavapizdec.R
import com.example.rxjavapizdec.models.Note
import com.example.rxjavapizdec.other.byteArrayToBitmap


class MainFragmentRecViewAdapter(private var listener: OnItemClickListener) :
    RecyclerView.Adapter<MainFragmentRecViewAdapter.ViewHolder>() {
    private var notes: MutableList<Note> = mutableListOf()

    interface OnItemClickListener {
        fun onClick(pos:Int)
    }

   inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        val title = view.findViewById<TextView>(R.id.fragment_main_note_title)
        val date = view.findViewById<TextView>(R.id.fragment_main_note_date)
        val thumbnail = view.findViewById<ImageView>(R.id.thumbnail)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if(adapterPosition!=RecyclerView.NO_POSITION){
                listener.onClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_fragment_main, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var title = notes[position].title
        if (notes[position].title.isEmpty()) {
            title = notes[position].description
        }
        holder.title.text = title
        holder.thumbnail.setImageBitmap(byteArrayToBitmap(notes[position].thumbnail))
        holder.date.text = notes[position].date
    }

    override fun getItemCount(): Int = notes.size

    fun update(note: List<Note>) {
        notes.clear()
        notes.addAll(note)
        notifyDataSetChanged()
    }

    fun remove(note: Note) {
        notes.remove(note)
        notifyDataSetChanged()
    }

    fun removeAt(pos:Int){
        notes.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun getItemAt(pos: Int):Note{
        return notes[pos]
    }
}