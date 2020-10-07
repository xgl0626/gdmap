package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.gdmap.bean.AnswerTestData

/*
   answeractivity和reply网络请求事件处理
 */
class CommentViewModel : ViewModel() {

   private var isExcite=MutableLiveData<Boolean>()
   private var isCollect=MutableLiveData<Boolean>()
}