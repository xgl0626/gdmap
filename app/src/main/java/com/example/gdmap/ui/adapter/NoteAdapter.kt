package com.example.gdmap.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gdmap.R
import com.example.gdmap.database.NoteDataBase
import com.example.gdmap.database.NotesBook
import com.example.gdmap.ui.activity.NoteContentActivity
import com.example.gdmap.utils.MyApplication
import kotlin.concurrent.thread

class NoteAdapter(val context: Context, val noteList: ArrayList<NotesBook>) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById(R.id.tv_note_content)
        val noteTime: TextView = itemView.findViewById(R.id.tv_note_time)
        val noteSingleClear: ImageView = itemView.findViewById(R.id.iv_note_single_clear)
        fun onClick(noteList: ArrayList<NotesBook>) {
            itemView.setOnClickListener { view ->
                val intent = Intent(view.context, NoteContentActivity::class.java)
                intent.putExtra("id", noteList[layoutPosition].id)
                intent.putExtra("point", 1)
                view.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        val viewHolder = NoteViewHolder(view)
        viewHolder.onClick(noteList)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.noteTitle.text = noteList[position].title
        holder.noteTime.text = noteList[position].time
        holder.noteSingleClear.setOnClickListener { view ->
            val notedao = NoteDataBase.getDatabase(MyApplication.context).noteDao()
            if (noteList.size > 0) {
               thread {
                   notedao.deleteNotes(noteList[position])
                   noteList.removeAt(position)
               }
                notifyDataSetChanged()
            }
        }
    }
}