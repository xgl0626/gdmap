package com.example.gdmap.ui.viewholder

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gdmap.database.ArticleDataBase
import com.example.gdmap.ui.activity.ArticleContentActivity
import com.example.gdmap.ui.widget.RectangleView
import kotlinx.android.synthetic.main.article_item.view.*

class ArticleViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
{
    val tv_article_item_title:TextView=itemView.tv_article_item_title
    val tv_article_item_author:TextView=itemView.tv_article_item_author
    val tv_article_item_date:TextView=itemView.tv_article_item_date
    val iv_article_item_1:RectangleView=itemView.iv_article_item1
    val iv_article_item_2:RectangleView=itemView.iv_article_item2
    val iv_article_item_3:RectangleView=itemView.iv_article_item3
    val iv_article_item_4:RectangleView=itemView.iv_article_item4
  fun onClick(articleDataList:ArrayList<ArticleDataBase.Data>) {
        itemView.setOnClickListener {view ->
            val intent = Intent(view.context, ArticleContentActivity::class.java)
            intent.putExtra("url", articleDataList.get(layoutPosition).url)
            view.context.startActivity(intent)
        }
    }

}