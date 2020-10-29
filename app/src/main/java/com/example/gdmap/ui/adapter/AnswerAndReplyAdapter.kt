package com.example.gdmap.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gdmap.R
import com.example.gdmap.bean.AnswerData
import com.example.gdmap.bean.CommentData
import com.example.gdmap.config.TokenConfig.BASE_URL
import com.example.gdmap.ui.widget.CircleImageView
import com.example.gdmap.utils.isLike
import com.example.gdmap.utils.setOnSingleClickListener

/**
 * @Author: xgl
 * @ClassName: AnswerAndReplyAdapter
 * @Description:
 * @Date: 2020/10/6 20:04
 */
class AnswerAndReplyAdapter(val context: Context) :
    BaseExpandableListAdapter() {
    private val answerList = ArrayList<AnswerData>()
    var onPraiseClickListener: ((View, AnswerData) -> Unit)? = null
    var onGroupClickListener: ((Int, AnswerData) -> Unit)? = null

    override fun getGroupCount(): Int {
        return answerList.size
    }

    override fun getChildrenCount(i: Int): Int {
        return if (answerList[i].replyList == null) {
            0
        } else {
            if (answerList[i].replyList?.isNotEmpty()!!) answerList[i].replyList
                ?.size!! else 0
        }
    }

    override fun getGroup(i: Int): Any {
        return answerList[i]
    }

    override fun getChild(i: Int, i1: Int): CommentData? {
        return answerList[i].replyList?.get(i1)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return getCombinedChildId(groupPosition.toLong(), childPosition.toLong())
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpand: Boolean,
        convertView: View?,
        viewGroup: ViewGroup?
    ): View? {
        var convertView = convertView
        val groupHolder: GroupHolder
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.item_person_answer, viewGroup, false)
            groupHolder = GroupHolder(convertView)
            convertView!!.tag = groupHolder
        } else {
            groupHolder = convertView.tag as GroupHolder
        }
        groupHolder.apply {
            Glide.with(context).load(BASE_URL + answerList[groupPosition].photo_avatar)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .into(avatar)
            author.text = answerList[groupPosition].nickname
            time.text = answerList[groupPosition].created_at
            content.text = answerList[groupPosition].description
            itemView.setOnClickListener {
                onGroupClickListener?.invoke(groupPosition, answerList[groupPosition])
            }
            excite.isLike(answerList[groupPosition].answer_id)
            excite.setOnSingleClickListener {
                onPraiseClickListener?.invoke(it, answerList[groupPosition])
            }
        }
        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        b: Boolean,
        convertView: View?,
        viewGroup: ViewGroup?
    ): View? {
        var convertView = convertView
        val childHolder: ChildHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                .inflate(R.layout.item_reply, viewGroup, false)
            childHolder = ChildHolder(convertView)
            convertView!!.tag = childHolder
        } else {
            childHolder = convertView.tag as ChildHolder
        }
        val replyUser: String? =
            answerList[groupPosition].replyList?.get(childPosition)?.nickname
        if (!TextUtils.isEmpty(replyUser)) {
            childHolder.author.text = "$replyUser:"
        } else {
            childHolder.author.text = "无名" + ":"
        }
        childHolder.content.text =
            answerList[groupPosition].replyList?.get(childPosition)?.description
        return convertView
    }

    override fun isChildSelectable(i: Int, i1: Int): Boolean {
        return true
    }

    class GroupHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar = view.findViewById<CircleImageView>(R.id.iv_avatar)
        val author = view.findViewById<TextView>(R.id.tv_authorName)
        val content = view.findViewById<TextView>(R.id.tv_answerContent)
        val time = view.findViewById<TextView>(R.id.tv_date)
        val excite = view.findViewById<ImageView>(R.id.iv_excite)
    }

    class ChildHolder(view: View) : RecyclerView.ViewHolder(view) {

        val author = view.findViewById<TextView>(R.id.reply_item_user)
        val content = view.findViewById<TextView>(R.id.reply_item_content)
    }

    /**
     * func:回答成功后插入一条数据
     * @param answerBean 新的评论数据
     */
    fun addAnswerData(answerBean: AnswerData?) {
        if (answerBean != null) {
            answerList.add(answerBean)
            notifyDataSetChanged()
        } else {
            throw IllegalArgumentException("回答数据为空!")
        }
    }

    /*
        添加所有回答数据
     */
    fun addData(newDataLists: List<AnswerData>) {
        answerList.clear()
        initRefreshImages(newDataLists)
    }

    /*
        刷新回答数据
     */

    fun initRefreshImages(dataLists: List<AnswerData>) {
        answerList.addAll(dataLists)
        notifyDataSetChanged()
    }

    /**
     * func:回复成功后插入一条数据
     * @param replyBean 新的回复数据
     */
    fun addReplyData(replyBean: CommentData?, groupPosition: Int) {
        if (replyBean != null) {
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:$replyBean")
            if (answerList[groupPosition].replyList != null) {
                answerList[groupPosition].replyList?.add(replyBean)
            } else {
                val replyList: MutableList<CommentData> = ArrayList()
                replyList.add(replyBean)
                answerList[groupPosition].replyList?.addAll(replyList)
            }
            notifyDataSetChanged()
        } else {
            throw IllegalArgumentException("回复数据为空!")
        }
    }

    /**
     * func:添加和展示所有回复
     * @param replyBeanList 所有回复数据
     * @param groupPosition 当前的评论
     */
    fun addAllReplyList(replyBeanList: List<CommentData>, groupPosition: Int) {
        answerList[groupPosition].replyList?.clear()
        answerList[groupPosition].replyList?.addAll(replyBeanList)
        notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "CommentExpandAdapter"
    }
}