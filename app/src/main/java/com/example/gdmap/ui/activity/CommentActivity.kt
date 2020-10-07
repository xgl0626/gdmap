package com.example.gdmap.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.bean.ReplyBean
import com.example.gdmap.ui.adapter.AnswerAndReplyAdapter
import com.example.gdmap.ui.adapter.OnItemOnClick
import com.example.gdmap.ui.viewmodel.QuestionViewModel
import com.example.gdmap.utils.excite
import com.example.gdmap.utils.favorite
import com.example.gdmap.utils.setOnSingleClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.dialog_comment_layout.view.*

/**
 * @Author: 徐国林
 * @ClassName: questionListActivity
 * @Description:
 * @Date: 2020/9/5 21:56
 */
class CommentActivity : BaseActivity() {
    private var answerAndReplyAdapter: AnswerAndReplyAdapter? = null
    private val viewModel by lazy { ViewModelProviders.of(this).get(QuestionViewModel::class.java) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_comment
    }

    override fun initData() {
        viewModel.getQuestionData()
        viewModel.getAnswerAndReplyData()
        viewModel.commentData.observe(this, Observer {
            tv_question_author.text = it[0].author
            tv_question_content.text = it[0].content
            tv_question_time.text = it[0].time
            tv_question_title.text = it[0].title
        })
        viewModel.messageData.observe(this, Observer {
            answerAndReplyAdapter?.addData(it)
        })
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        initAnswerAndReply()
    }

    private fun initAnswerAndReply() {
        answerAndReplyAdapter = AnswerAndReplyAdapter(this)
        comment_expand_list_view.setGroupIndicator(null)
        //默认展开所有回复
        comment_expand_list_view.setAdapter(answerAndReplyAdapter)
    }

    override fun initClick() {
        answerAndReplyAdapter?.setOnItemClick(object : OnItemOnClick {
            override fun onClick(view: View, groupPosition: Int, string: String) {
                if (string == "查看回复")
                    comment_expand_list_view.expandGroup(groupPosition)
                else
                    comment_expand_list_view.collapseGroup(groupPosition)
            }
        })

        comment_expand_list_view.setOnGroupClickListener { expandableListView, view, groupPosition, l ->
            Toast.makeText(this, "点击了回答", Toast.LENGTH_SHORT).show()
            showReply(groupPosition)
            true
        }

        comment_expand_list_view.setOnGroupExpandListener {

        }

        comment_expand_list_view.setOnChildClickListener{ expandableListView, view, groupPosition, childPosition, l ->
            Toast.makeText(this, "点击了回复", Toast.LENGTH_SHORT).show()
            false
        }

        ib_answerButton.setOnSingleClickListener {
            changeToActivity(WriteAnswerActivity())
        }
        ib_excitingButton.excite()
        ib_favoriteButton.favorite()
    }

    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    private fun showReply(groupPosition: Int) {
        Log.d("tagtagtag", "123458")
        val dialog = BottomSheetDialog(this)
        val replyView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_comment_layout, null)
        dialog.setContentView(replyView)
        /**
         * 解决bsd显示不全的情况
         */
        val parent = replyView.parent as View
        val behavior = BottomSheetBehavior.from(parent)
        replyView.measure(0, 0)
        behavior.peekHeight = replyView.measuredHeight
        replyView.apply {
            bt_dialog_send.setOnSingleClickListener {
                val replyContent = et_comment_content.text.toString().trim { it <= ' ' }
                if (!TextUtils.isEmpty(replyContent)) {
                    dialog.dismiss()
                    val detailBean = ReplyBean("小红", replyContent)
                    answerAndReplyAdapter?.addReplyData(detailBean, groupPosition)
                    comment_expand_list_view.expandGroup(groupPosition, true)
                    com.example.gdmap.utils.Toast.toast("回复成功")
                } else {
                    com.example.gdmap.utils.Toast.toast("回复内容不能为空")
                }
            }
            et_comment_content.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    if (!TextUtils.isEmpty(charSequence) && charSequence.length > 2) {
                        bt_dialog_send.setBackgroundColor(Color.parseColor("#FFB568"))
                    } else {
                        bt_dialog_send.setBackgroundColor(Color.parseColor("#D8D8D8"))
                    }
                }

                override fun afterTextChanged(editable: Editable) {}
            })
        }
        dialog.show()
    }
}