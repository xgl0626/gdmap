package com.example.gdmap.ui.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.bean.AnswerData
import com.example.gdmap.bean.CommentData
import com.example.gdmap.config.TokenConfig.BASE_URL
import com.example.gdmap.ui.adapter.AnswerAndReplyAdapter
import com.example.gdmap.ui.viewmodel.CommentViewModel
import com.example.gdmap.utils.*
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
    companion object {
        const val QUESTION = "question"
        const val ANSWER = "answer"
    }

    private var questionId: Int? = null
    private var answerAndReplyAdapter: AnswerAndReplyAdapter? = null
    private val viewModel by lazy { ViewModelProviders.of(this).get(CommentViewModel::class.java) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_comment
    }

    override fun initData() {
        questionId = intent.getIntExtra("questionId", 0)
        if (questionId != null) {
            viewModel.getQuestionContent(questionId!!)
        }
        viewModel.question.observe(this, Observer {
            it.apply {
                tv_question_author.text = nickname
                tv_question_content.text = description
                tv_question_time.text = created_at
                tv_question_title.text = tittle
                Glide.with(iv_avatar)
                    .load(BASE_URL + photo_avatar).into(iv_avatar)
            }
        })
        questionId?.let { viewModel.getAnswerList(it) }
        viewModel.answerListData.observe(this, Observer {
            answerAndReplyAdapter?.addData(it)
            initReplyData(it)
        })
    }

    fun initReplyData(answerListData: List<AnswerData>) {
        for (i in answerListData.indices) {
            comment_expand_list_view.expandGroup(i)
        }
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        initAnswerAndReply()
    }

    private fun initAnswerAndReply() {
        answerAndReplyAdapter = AnswerAndReplyAdapter(this).apply {
            onGroupClickListener = { groupPosition, answerdata ->
//                showReply(groupPosition, answerdata)
            }
            onPraiseClickListener = { view, answerData ->
                view.excite(answerData.answer_id)
                answerData.answer_id.let { it1 -> viewModel.like(it1, ANSWER) }

            }
        }
        comment_expand_list_view.setGroupIndicator(null)
        //默认展开所有回复
        comment_expand_list_view.setAdapter(answerAndReplyAdapter)
    }

    override fun initClick() {
        if (questionId != null) {
            iv_excitingButton.isLike(questionId!!)
            iv_favoriteButton.isCollected(questionId!!)
        }
        iv_excitingButton.setOnSingleClickListener {
            questionId.let { it1 ->
                if (it1 != null) {
                    viewModel.like(it1, QUESTION)
                }
            }
            questionId?.let { it2 -> it.excite(it2) }
        }

        iv_favoriteButton.setOnSingleClickListener {
            questionId.let { it1 ->
                if (it1 != null) {
                    viewModel.collect(it1)
                }
            }
            questionId?.let { it1 -> it.favorite(it1) }
        }

        tv_answer_or_reply.setOnSingleClickListener {
            questionId.let { it1 ->
                if (it1 != null) {
                    showAnswer(it1)
                }
            }
        }
    }

    private fun showReply(groupPosition: Int, answerData: AnswerData) {
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
                    answerAndReplyAdapter?.addReplyData(
                        CommentData(
                            5,
                            "2020-10-20-08:10:04",
                            "2020-10-20-08:10:04",
                            replyContent,
                            "xgl",
                            null,
                            "2020-10-20-08:10:04"
                        ), groupPosition
                    )
                    comment_expand_list_view.expandGroup(groupPosition)
                    dialog.dismiss()
                } else {
                    Toast.toast("回复内容不能为空")
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

                override fun onTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
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

    override fun onRestart() {
        super.onRestart()
        Log.d("tagtag", "onRestart")
        questionId?.let { viewModel.getAnswerList(it) }
    }

    private fun showAnswer(questionId: Int) {
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
            et_comment_content.hint = "请输入回答内容..."
            bt_dialog_send.setOnSingleClickListener {
                val answerContent = et_comment_content.text.toString().trim { it <= ' ' }
                if (!TextUtils.isEmpty(answerContent)) {
                    viewModel.addAnswer(questionId, answerContent)
                    sendMsg(0)
                    dialog.dismiss()
                } else {
                    Toast.toast("回答内容不能为空")
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

                override fun onTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
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

    fun sendMsg(index: Int) {
        val message = Message()
        message.what = index
        handler.sendMessage(message)
    }

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0)
                questionId?.let { viewModel.getAnswerList(it) }
        }
    }

}

