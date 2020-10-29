package com.example.gdmap.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gdmap.R
import com.example.gdmap.bean.QuestionData
import com.example.gdmap.config.TokenConfig.BASE_URL
import com.example.gdmap.ui.activity.CommentActivity
import com.example.gdmap.ui.activity.ViewImageActivity
import com.example.gdmap.ui.widget.CircleImageView
import com.example.gdmap.ui.widget.NineGridView
import com.example.gdmap.utils.setOnSingleClickListener


/**
 * @Author: 徐国林
 * @ClassName: ImageItemAdapter
 * @Description:
 * @Date: 2020/8/31 22:30
 */
class QuestionItemAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val HEADER = 0
        const val LOAD = 1
    }

    private var data = ArrayList<QuestionData>()

    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar = view.findViewById<CircleImageView>(R.id.iv_avatar)
        val author = view.findViewById<TextView>(R.id.tv_authorName)
        val title = view.findViewById<TextView>(R.id.tv_questionTitle)
        val content = view.findViewById<TextView>(R.id.tv_questionDetail)
        val time = view.findViewById<TextView>(R.id.tv_date)
        val nine_views = view.findViewById<NineGridView>(R.id.nine_grid_view)
        val place = view.findViewById<TextView>(R.id.tv_place)


    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomore = view.findViewById<TextView>(R.id.tv_no_more)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1)
            HEADER
        else
            LOAD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LOAD -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_person_comment,
                    parent,
                    false
                )
                return CommentViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_nomore,
                    parent,
                    false
                )
                return HeaderViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            HEADER -> {
                val viewHolder = holder as HeaderViewHolder
                viewHolder.apply {
                    nomore.text = "没有更多了"
                }
            }

            LOAD -> {
                val viewHolder = holder as CommentViewHolder
                viewHolder.setIsRecyclable(false)
                Log.d("photo", data[position].photo_avatar)
                viewHolder.apply {
                    Glide.with(avatar)
                        .load(BASE_URL + data[position].photo_avatar).into(avatar)
                    time.text = data[position].created_at
                    title.text = data[position].tittle
                    author.text = data[position].nickname
                    content.text = data[position].description
                    place.text = data[position].place
                    nine_views.setImages(data[position].photo_url)
                    nine_views.setOnItemClickListener { itemView, index ->
                        val iv = itemView as ImageView
                        val bitmap = iv.drawToBitmap()
                        val intent = Intent(context, ViewImageActivity::class.java)
                        intent.putExtra("url", bitmap)
                        context.startActivity(intent)
                    }
                    itemView.setOnSingleClickListener {
                        changeToActivity(CommentActivity(), data[position].question_id)
                    }
                }
            }
        }
    }

    private fun changeToActivity(activity: Activity, questionId: Int) {
        val intent = Intent(context, activity::class.java)
        intent.putExtra("questionId", questionId)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    fun addData(newDataLists: List<QuestionData>) {
        data.clear()
        initRefreshImages(newDataLists)
    }

    fun initRefreshImages(dataLists: List<QuestionData>) {
        data.addAll(dataLists)
        notifyDataSetChanged()
    }

}
