package com.example.gdmap.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.services.core.PoiItem
import com.bumptech.glide.Glide
import com.example.gdmap.R
import com.example.gdmap.ui.viewholder.ServiceViewHolder

class ServiceAdapter(
    val lat: Double?, val lng: Double?,
    val context: Context?,
    val poiList: ArrayList<PoiItem>
):RecyclerView.Adapter<ServiceViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.search_item,parent,false)
        val serviceViewHolder= ServiceViewHolder(view)
        if (lng != null) {
            if (lat != null) {
                serviceViewHolder.startGulide(lat,lng,poiList)
            }
        }
        return  serviceViewHolder
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.serviceItemplace.text = poiList.get(position).toString()
        holder.serviceItemPlaceContent.text = poiList.get(position).toString()
        holder.searviceItemDistance.text = poiList.get(position).distance.toString()+"米"
        if (poiList[position].photos.toString() != "[]") {
            Glide.with(context).load(poiList[position].photos[0].url)
                .into(holder.serviceItemImageView)
        }else
        {
            Glide.with(context).load(R.drawable.image)
                .into(holder.serviceItemImageView)
        }
    }

    override fun getItemCount(): Int {
       return poiList.size
    }

}