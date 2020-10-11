package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.gdmap.bean.CommentData
import com.example.gdmap.bean.QuestionData
import com.example.gdmap.network.ServiceCreator
import com.example.gdmap.network.ApiService
import com.example.gdmap.utils.Toast
import okhttp3.MultipartBody
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/*
   问题详情界面，提供评论和回答，点赞和收藏
   answeractivity和reply网络请求事件处理
 */
class CommentViewModel : ViewModel() {

    private val question = MutableLiveData<QuestionData>()
    private val commentList = MutableLiveData<List<CommentData>>()
    private var isExcite = MutableLiveData<Boolean>()
    private var isCollect = MutableLiveData<Boolean>()

    fun getQuestionContent(question_id: Int) {
        ServiceCreator.create(ApiService::class.java)
            ?.getQuestionContent(question_id)
            ?.subscribeOn(Schedulers.io())
            ?.unsubscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                if (it.status == 10000) {
                    question.value = it.data
                } else {
                    Toast.toast("获取失败")
                }
            }
    }

    fun addComment(token: String, answer_id: Int, description: String) {
        ServiceCreator.create(ApiService::class.java)
            ?.addComment(token, answer_id, description)
            ?.subscribeOn(Schedulers.io())
            ?.unsubscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                if (it.status == 10000) {
                    Toast.toast("评论成功")
                } else {
                    Toast.toast("评论失败")
                }
            }
    }

    fun getCommentList(token: String, answer_id: Int) {
        ServiceCreator.create(ApiService::class.java)
            ?.getCommentList(token, answer_id)
            ?.subscribeOn(Schedulers.io())
            ?.unsubscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                if (it.status == 10000) {
                    commentList.value = it.data
                } else {
                    Toast.toast("获取评论失败")
                }
            }
    }

    fun addAnswer(
        token: String,
        title: String,
        description: String,
        photoList: List<MultipartBody.Part>
    ) {
        ServiceCreator.create(ApiService::class.java)
            ?.addAnswer(token, title, description,photoList)
            ?.subscribeOn(Schedulers.io())
            ?.unsubscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                if (it.status == 10000) {
                    Toast.toast("回答成功")
                } else {
                    Toast.toast("回答失败")
                }
            }
    }

    fun like(token: String, id: Int, type: String) {
        ServiceCreator.create(ApiService::class.java)
            ?.like(token, id, type)
            ?.subscribeOn(Schedulers.io())
            ?.unsubscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                if (it.status == 10000) {
                    Toast.toast("点赞成功")
                } else {
                    Toast.toast("点赞失败")
                }
            }
    }

    fun collect(token: String, id: Int) {
        ServiceCreator.create(ApiService::class.java)
            ?.collect(token, id)
            ?.subscribeOn(Schedulers.io())
            ?.unsubscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                if (it.status == 10000) {
                    Toast.toast("收藏成功")
                } else {
                    Toast.toast("收藏失败")
                }
            }
    }
}