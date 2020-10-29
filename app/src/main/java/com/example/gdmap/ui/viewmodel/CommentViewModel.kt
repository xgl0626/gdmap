package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.bean.AnswerData
import com.example.gdmap.bean.QuestionData
import com.example.gdmap.config.TokenConfig
import com.example.gdmap.network.ApiService
import com.example.gdmap.network.ServiceCreator
import com.example.gdmap.utils.Toast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/*
   问题详情界面，提供评论和回答，点赞和收藏
   answeractivity和reply网络请求事件处理
 */
class CommentViewModel : ViewModel() {

    val question = MutableLiveData<QuestionData>()
    val answerListData = MutableLiveData<List<AnswerData>>()
    val commentList = MutableLiveData<Boolean>()
    private var token2: String? = null

    init {
        token2 = "Bearer ${TokenConfig.token.token}"
    }

    fun getAnswerList(question_id: Int) {

        token2?.let {
            ServiceCreator.create(ApiService::class.java)
                .getAnswerList(it, question_id)
                .subscribeOn(Schedulers.io())
                ?.unsubscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe {
                    if (it.status == 10000) {
                        answerListData.value = it.data
                    } else {
                        Toast.toast("获取失败")
                    }
                }
        }
    }

    fun getQuestionContent(question_id: Int) {
        ServiceCreator.create(ApiService::class.java)
            .getQuestionContent(question_id)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.status == 10000) {
                    question.value = it.data
                } else {
                    Toast.toast("获取失败")
                }
            }
    }

    fun addComment(answer_id: Int, description: String) {
        token2?.let {
            ServiceCreator.create(ApiService::class.java)
                .addComment(it, answer_id, description)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.status == 10000) {
                        Toast.toast("评论成功")
                    } else {
                        Toast.toast("评论失败")
                    }
                }
        }
    }

//    fun bindCommentList() {
//
//    }
//
//    fun getCommentList(answerListData: List<AnswerData>) {
//        answerListData.forEach { answer ->
//            token2?.let { it ->
//                ServiceCreator.create(ApiService::class.java)
//                    .getCommentList(it, answer.answer_id)
//                    .subscribeOn(Schedulers.io())
//                    .unsubscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe {
//                        if (it.status == 10000) {
//                            TokenConfig.bindReplyData.put(answer.answer_id,it.data)
//                        } else {
//                            Toast.toast("获取评论失败")
//                        }
//                    }
//            }
//        }
//    }


    fun addAnswer(
        question_id: Int,
        description: String
    ) {
        token2?.let {
            ServiceCreator.create(ApiService::class.java)
                .addAnswer(it, question_id, description)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.status == 10000) {
                    } else {
                        Toast.toast("回答失败")
                    }
                }
        }
    }

    fun like(id: Int, type: String) {
        token2?.let {
            ServiceCreator.create(ApiService::class.java)
                .like(it, id, type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.status == 10000) {
                    } else {
                        Toast.toast("点赞失败")
                    }
                }
        }
    }

    fun collect(id: Int) {
        token2?.let {
            ServiceCreator.create(ApiService::class.java)
                .collect(it, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.status == 10000) {
                    } else {
                        Toast.toast("收藏失败")
                    }
                }
        }
    }
}