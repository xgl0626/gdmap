package com.example.gdmap.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gdmap.R
import com.example.gdmap.bean.QuestionData
import com.example.gdmap.bean.Tip

/**
 *@Date 2020-10-23
 *@Time 21:13
 *@author SpreadWater
 *@description
 */
class TipsAdapter(val context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.item_tip,parent,false)
        return TipsViewHolder(view)
    }
    private var data = ArrayList<Tip>()
    private var tittle= ""
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder=holder as TipsViewHolder
        viewHolder.title.text=tittle
        viewHolder.description.text=data[position].description
    }

    override fun getItemCount()=data.size

    class TipsViewHolder(view:View):RecyclerView.ViewHolder(view){
        val title=view.findViewById<TextView>(R.id.tv_title)
        val description=view.findViewById<TextView>(R.id.tv_answerContent)
    }
    fun addData(newDataLists: List<Tip>,tittle:String) {
        data.clear()
        initRefreshImages(newDataLists,tittle)
    }
    fun initRefreshImages(dataLists: List<Tip>,tittle:String) {
        data.addAll(dataLists)
        this.tittle=tittle
        notifyDataSetChanged()
    }
}