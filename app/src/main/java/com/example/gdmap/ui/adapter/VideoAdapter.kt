package com.example.gdmap.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gdmap.R
import com.example.gdmap.database.VideoDataBase
import com.example.gdmap.ui.viewholder.VideoViewHolder




class VideoAdapter(val context: Context?, val datas:List<VideoDataBase.Result>) : RecyclerView.Adapter<VideoViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.video_item,parent,false)
        val viewHolder=VideoViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.iv_videoView_item.setUp(datas[position].data.playUrl,datas[position].data.title)
        Glide.with(context).load(datas[position].data.cover.feed).into(holder.iv_videoView_item.thumbImageView)
    }

}