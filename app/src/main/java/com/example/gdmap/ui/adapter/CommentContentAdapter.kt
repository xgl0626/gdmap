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
import com.example.gdmap.database.AnswerTestData
import com.example.gdmap.database.MessagesTestData
import com.example.gdmap.ui.activity.WriteAnswerActivity
import com.example.gdmap.ui.widget.CircleImageView
import com.example.gdmap.utils.excite
import com.example.gdmap.utils.favorite
import com.example.gdmap.utils.naive

/**
 * @Author: xgl
 * @ClassName: CommentContentAdapter
 * @Description:
 * @Date: 2020/10/4 14:40
 */
class CommentContentAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val HEADER = 0
        const val ANSWER = 1
        const val QUESTION = 2
    }

    private var questionData: MessagesTestData? = null
    private var data = ArrayList<AnswerTestData>()

    class AnswerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar = view.findViewById<CircleImageView>(R.id.iv_avatar)
        val author = view.findViewById<TextView>(R.id.tv_authorName)
        val content = view.findViewById<TextView>(R.id.tv_answerContent)
        val time = view.findViewById<TextView>(R.id.tv_date)
        val excite=view.findViewById<ImageButton>(R.id.ib_excitingButton)
        val naive =view.findViewById<ImageButton>(R.id.ib_naiveButton)
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomore = view.findViewById<TextView>(R.id.tv_no_more)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount - 1 -> HEADER
            0 -> QUESTION
            else -> ANSWER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            QUESTION -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_person_comment,
                    parent,
                    false
                )
                return ServiceItemAdapter.CommentViewHolder(view)
            }

            ANSWER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_person_answer, parent, false)
                return AnswerViewHolder(view)
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

            QUESTION -> {
                val viewHolder = holder as ServiceItemAdapter.CommentViewHolder
                viewHolder.apply {
                    Glide.with(avatar)
                        .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_image))
                        .load(R.drawable.ic_image).into(avatar)
                    time.text = questionData?.time
                    title.text = questionData?.title
                    author.text = questionData?.author
                    content.text = questionData?.content
                    write.setOnClickListener {
                        changeToActivity(WriteAnswerActivity())
                    }
                    fravorite.favorite()
                    excite.excite()
                    naive.naive()
                }
            }

            ANSWER -> {
                val viewHolder = holder as AnswerViewHolder
                viewHolder.apply {
                    Glide.with(avatar)
                        .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_image))
                        .load(R.drawable.ic_image).into(avatar)
                    time.text = data[position-1].time
                    author.text = data[position-1].author
                    content.text = data[position-1].content
                    excite.excite()
                    naive.naive()
                }
            }
        }
    }

    private fun changeToActivity(activity: Activity) {
        val intent = Intent(context, activity::class.java)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return data.size + 2
    }

    fun addCommentItem(newQuestionData: MessagesTestData) {
        this.questionData = newQuestionData
    }

    fun addData(newDataLists: List<AnswerTestData>) {
        data.clear()
        initRefreshImages(newDataLists)
    }

    fun initRefreshImages(dataLists: List<AnswerTestData>) {
        data.addAll(dataLists)
        notifyDataSetChanged()
    }
}