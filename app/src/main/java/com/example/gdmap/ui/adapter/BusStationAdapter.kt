package com.example.gdmap.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.services.busline.BusLineItem
import com.amap.api.services.busline.BusStationItem
import com.example.gdmap.R
import com.example.gdmap.database.ArticleDataBase

class BusStationAdapter(val context:Context) :RecyclerView.Adapter<BusStationAdapter.BusStationViewHolder>()
{
    private var busStationList=ArrayList<BusStationItem>()
    private var busLineList=ArrayList<BusLineItem>()
    class BusStationViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val tv_bus_station_name: TextView =itemView.findViewById(R.id.tv_bus_station_name)
        val tv_bus_station_content: TextView =itemView.findViewById(R.id.tv_bus_station_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStationViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.bus_station_item,parent,false)
        return BusStationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return busStationList.size
    }

    override fun onBindViewHolder(holder: BusStationViewHolder, position: Int) {
       for(busStationItem in busStationList[position].busLineItems)
        holder.tv_bus_station_content.text=busStationItem.busLineName
        holder.tv_bus_station_name.text=busStationList[position].busStationName

//        holder.tv_bus_station_content.text=busLineList[0].originatingStation+"-"+busLineList[0].terminalStation
//        holder.tv_bus_station_name.text=busLineList[0].busLineName
    }
    fun addDataList(dataStationList: ArrayList<BusStationItem>,dataLineList:ArrayList<BusLineItem>) {
        busStationList.clear()
//        busLineList.clear()
//        busLineList.addAll(busLineList)
        busStationList.addAll(dataStationList)
        notifyDataSetChanged()
    }
}