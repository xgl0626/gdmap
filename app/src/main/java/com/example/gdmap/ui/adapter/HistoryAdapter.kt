package com.example.gdmap.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gdmap.R
import com.example.gdmap.database.PlayDataBase

class HistoryAdapter(val context: Context,val data:ArrayList<PlayDataBase.NewsList>) :RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>()
{
    class HistoryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val tv_history_time:TextView=itemView.findViewById(R.id.tv_history_time)
        val tv_history_data:TextView=itemView.findViewById(R.id.tv_history_content)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.tv_history_data.text=data[position].title
        holder.tv_history_time.text=data[position].lsdate

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.historydata_item,parent,false)
        return HistoryViewHolder(view)
    }

}