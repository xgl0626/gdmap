package com.example.gdmap.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.question_item.view.*

class RobotViewHolder (itemView: View):RecyclerView.ViewHolder(itemView)
{
    val tv_question_left:TextView=itemView.tv_question_left
    val tv_question_right:TextView=itemView.tv_question_right

}