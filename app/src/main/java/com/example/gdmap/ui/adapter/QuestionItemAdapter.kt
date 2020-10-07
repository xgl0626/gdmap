package com.example.gdmap.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gdmap.R
import com.example.gdmap.bean.MessagesTestData
import com.example.gdmap.ui.activity.CommentActivity
import com.example.gdmap.ui.activity.WriteAnswerActivity
import com.example.gdmap.ui.widget.CircleImageView
import com.example.gdmap.ui.widget.NineGridView
import com.example.gdmap.utils.excite
import com.example.gdmap.utils.favorite


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

    private var data = ArrayList<MessagesTestData>()

    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar = view.findViewById<CircleImageView>(R.id.iv_avatar)
        val author = view.findViewById<TextView>(R.id.tv_authorName)
        val title = view.findViewById<TextView>(R.id.tv_questionTitle)
        val content = view.findViewById<TextView>(R.id.tv_questionDetail)
        val time = view.findViewById<TextView>(R.id.tv_date)
        val write = view.findViewById<ImageButton>(R.id.ib_answerButton)
        val fravorite = view.findViewById<ImageButton>(R.id.ib_favoriteButton)
        val excite = view.findViewById<ImageButton>(R.id.ib_excitingButton)
        val nine_views = view.findViewById<NineGridView>(R.id.nine_grid_view)

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
                viewHolder.apply {
                    Glide.with(avatar)
                        .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_image))
                        .load(R.drawable.ic_image).into(avatar)
                    time.text = data[position].time
                    title.text = data[position].title
                    author.text = data[position].author
                    content.text = data[position].content
                    write.setOnClickListener {
                        changeToActivity(WriteAnswerActivity())
                    }
                    fravorite.favorite()
                    excite.excite()
//                    nine_views.setImages()
                }
            }
        }
        holder.itemView.setOnClickListener {
            changeToActivity(CommentActivity())
        }
    }

    private fun changeToActivity(activity: Activity) {
        val intent = Intent(context, activity::class.java)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    fun addData(newDataLists: List<MessagesTestData>) {
        data.clear()
        initRefreshImages(newDataLists)
    }

    fun initRefreshImages(dataLists: List<MessagesTestData>) {
        data.addAll(dataLists)
        notifyDataSetChanged()
    }

}
