package com.example.gdmap.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gdmap.R
import com.example.gdmap.database.ArticleDataBase
import com.example.gdmap.ui.viewholder.ArticleViewHolder

class ArticleAdapter(val context: Context) :
    RecyclerView.Adapter<ArticleViewHolder>() {
    private val data=ArrayList<ArticleDataBase.Data>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.article_item, parent, false
        )
        val articleViewHolder=ArticleViewHolder(view)
        articleViewHolder.onClick(data)
        return articleViewHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        loadImageView(
            holder.iv_article_item_1.context,
            data[position].thumbnail_pic_s,
            holder.iv_article_item_1
        )
        loadImageView(
            holder.iv_article_item_2.context,
            data[position].thumbnail_pic_s02,
            holder.iv_article_item_2
        )
        loadImageView(
            holder.iv_article_item_3.context,
            data[position].thumbnail_pic_s03,
            holder.iv_article_item_3
        )

        loadImageView(
            holder.iv_article_item_4.context,
            data[position].thumbnail_pic_s04,
            holder.iv_article_item_4
        )
        holder.tv_article_item_author.text = data[position].author_name
        holder.tv_article_item_date.text = data[position].date
        holder.tv_article_item_title.text = data[position].title
    }

    fun loadImageView(
        mContext: Context?,
        path: String?,
        mImageView: ImageView?
    ) {
        if (path != null)
            Glide.with(mContext).load(path).asBitmap()
                .into(mImageView)
    }
    fun refreshDataList(newArticleList:ArrayList<ArticleDataBase.Data>) {
        data.clear()
        addDataList(newArticleList)
    }

    fun addDataList(dataList: ArrayList<ArticleDataBase.Data>) {
        data.addAll(dataList)
        notifyDataSetChanged()
    }
}