package com.example.gdmap.ui.viewholder

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.services.core.PoiItem
import com.example.gdmap.ui.activity.GudieActivity
import com.example.gdmap.ui.widget.RectangleView
import com.example.gdmap.utils.MyApplication.Companion.context
import kotlinx.android.synthetic.main.search_item.view.*

class ServiceViewHolder (itemview: View):RecyclerView.ViewHolder(itemview)
{
        val serviceItemImageView: RectangleView = itemView.iv_search_item
        val serviceItemplace: TextView = itemview.tv_service_item_place
        val serviceItemPlaceContent: TextView = itemview.tv_service_item_place_content
        val searviceItemDistance: TextView = itemview.tv_search_item_distance
        val serviceItemGulide: ImageView = itemview.iv_service_item_nowtime_gulide
    fun startGulide(lat:Double,lng:Double,poilist:List<PoiItem>) {
        serviceItemGulide.setOnClickListener{ view ->
            val intent=Intent(context, GudieActivity::class.java)
            intent.putExtra("lat",lat)
            intent.putExtra("lng",lng)
            intent.putExtra("endlat",poilist.get(layoutPosition).latLonPoint.latitude)
            intent.putExtra("endlng", poilist.get(layoutPosition).latLonPoint.longitude)
            view.context.startActivity(intent)
        }
    }
}