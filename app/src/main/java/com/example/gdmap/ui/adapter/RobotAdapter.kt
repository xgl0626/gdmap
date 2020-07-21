package com.example.gdmap.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gdmap.R
import com.example.gdmap.ui.activity.Message
import com.example.gdmap.ui.viewholder.RobotViewHolder

class RobotAdapter(
    val context: Context, val data:List<Message>) :RecyclerView.Adapter<RobotViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RobotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.question_item, parent, false
        )
        val robotViewHolder=
            RobotViewHolder(view)
        return robotViewHolder
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: RobotViewHolder, position: Int) {
            if(data[position].temp==0) {
                holder.tv_question_right.visibility=View.VISIBLE
                holder.tv_question_left.visibility=View.GONE
                holder.tv_question_right.text = data[position].message
            }
            if(data[position].temp==1) {
                holder.tv_question_right.visibility=View.GONE
                holder.tv_question_left.visibility=View.VISIBLE
                holder.tv_question_left.text = data[position].message
            }
    }

}