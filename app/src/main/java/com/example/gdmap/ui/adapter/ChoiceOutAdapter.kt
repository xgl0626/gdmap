package com.example.gdmap.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gdmap.R
import com.example.gdmap.database.Choice
import com.example.gdmap.utils.GuideChoice
import com.example.gdmap.utils.RouteChoice
import kotlinx.android.synthetic.main.item_choice.*

/**
 *@Date 2020/10/6
 *@Time 22:26
 *@author SpreadWater
 *@description
 */
class ChoiceOutAdapter(val data:ArrayList<Choice>,val context:Context,val fragment:Fragment):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val choicedata=data
    private var choice:RouteChoice?=null
    fun setChoice(choice: RouteChoice){
        this.choice=choice
    }
    private var guidechoice:GuideChoice?=null
    fun setGuidechoice(choice: GuideChoice){
        this.guidechoice=choice
    }
    class ChoiceViewHolder(view: View):RecyclerView.ViewHolder(view){
        val out=view.findViewById<ImageView>(R.id.map_iv_out)
        val route=view.findViewById<ImageView>(R.id.map_iv_route)
        val guide=view.findViewById<ImageView>(R.id.map_iv_guide)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ChoiceViewHolder
        Glide.with(context).load(choicedata[position].out).into(viewHolder.out)
        //route的点击事件
        viewHolder.route.setOnClickListener { view ->
                choice?.Route(position)
        }
        //guide的点击事件
        viewHolder.guide.setOnClickListener { view->
            guidechoice?.Guide(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_choice,
            parent,
            false)
        return ChoiceViewHolder(view)
    }

    override fun getItemCount(): Int {
       return 2
    }
}