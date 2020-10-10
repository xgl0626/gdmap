package com.example.gdmap.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gdmap.R
import com.example.gdmap.bean.AnswerTestData
import com.example.gdmap.bean.ReplyBean
import com.example.gdmap.ui.widget.CircleImageView
import com.example.gdmap.utils.pressToZoomOut
import com.example.gdmap.utils.setOnSingleClickListener
import kotlin.collections.ArrayList

/**
 * @Author: xgl
 * @ClassName: AnswerAndReplyAdapter
 * @Description:
 * @Date: 2020/10/6 20:04
 */
class AnswerAndReplyAdapter(val context: Context) :
    BaseExpandableListAdapter() {
    var onSingleClick: OnItemOnClick? = null
    val answerList = ArrayList<AnswerTestData>()

    fun setOnItemClick(onItemOnClick: OnItemOnClick) {
        this.onSingleClick = onItemOnClick
    }

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

    override fun getChild(i: Int, i1: Int): ReplyBean? {
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

    var isLike = false
    var isExpand = false
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
            Glide.with(context).load(R.drawable.ic_image)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(avatar)
            author.text = answerList[groupPosition].author
            time.text = answerList[groupPosition].time
            content.text = answerList[groupPosition].content
            excite.setOnSingleClickListener {
                if (isLike) {
                    isLike = false
                    it.pressToZoomOut()
                } else {
                    isLike = true
                    it.pressToZoomOut()
                }
            }
            moreReply.setOnSingleClickListener {
                if (isExpand) {
                    this@AnswerAndReplyAdapter.isExpand = false
                    moreReply.text = "查看回复"
                    onSingleClick?.onClick(it, groupPosition, "收起")

                } else {
                    this@AnswerAndReplyAdapter.isExpand = true
                    moreReply.text = "收起"
                    onSingleClick?.onClick(it, groupPosition, "查看回复")
                }
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
            answerList[groupPosition].replyList?.get(childPosition)?.author
        if (!TextUtils.isEmpty(replyUser)) {
            childHolder.author.text = "$replyUser:"
        } else {
            childHolder.author.text = "无名" + ":"
        }
        childHolder.content.text = answerList[groupPosition].replyList?.get(childPosition)?.content
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
        val moreReply = view.findViewById<TextView>(R.id.tv_more_reply)
    }

    class ChildHolder(view: View) : RecyclerView.ViewHolder(view) {

        val author = view.findViewById<TextView>(R.id.reply_item_user)
        val content = view.findViewById<TextView>(R.id.reply_item_content)
    }

    /**
     * func:回答成功后插入一条数据
     * @param answerBean 新的评论数据
     */
    fun addAnswerData(answerBean: AnswerTestData?) {
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
    fun addData(newDataLists: List<AnswerTestData>) {
        answerList.clear()
        initRefreshImages(newDataLists)
    }

    /*
        刷新回答数据
     */

    fun initRefreshImages(dataLists: List<AnswerTestData>) {
        answerList.addAll(dataLists)
        notifyDataSetChanged()
    }

    /**
     * func:回复成功后插入一条数据
     * @param replyBean 新的回复数据
     */
    fun addReplyData(replyBean: ReplyBean?, groupPosition: Int) {
        if (replyBean != null) {
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:$replyBean")
            if (answerList[groupPosition].replyList != null) {
                answerList[groupPosition].replyList?.add(replyBean)
            } else {
                val replyList: MutableList<ReplyBean> = ArrayList<ReplyBean>()
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
    private fun addAllReplyList(replyBeanList: List<ReplyBean>, groupPosition: Int) {
        if (answerList[groupPosition].replyList != null) {
            answerList[groupPosition].replyList?.clear()
            answerList[groupPosition].replyList?.addAll(replyBeanList)
        } else {
            answerList[groupPosition].replyList?.addAll(replyBeanList)
        }
        notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "CommentExpandAdapter"
    }
}