package com.example.gdmap.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.bean.QuestionData
import com.example.gdmap.network.ServiceCreator
import com.example.gdmap.network.ApiService
import com.example.gdmap.utils.Toast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @Author: xgl
 * @ClassName: SearchViewModel
 * @Description:searchactivity 界面
 * @Date: 2020/10/7 9:40
 */
class SearchViewModel : ViewModel() {
    val questionList = MutableLiveData<List<QuestionData>>()

    fun search(title: String) {
        ServiceCreator.create(ApiService::class.java)
            ?.search(title)
            ?.subscribeOn(Schedulers.io())
            ?.unsubscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                if (it.status == 10000) {
                    questionList.value = it.data
                } else {
                    Toast.toast("未搜索到该内容")
                }
            }
    }
}
