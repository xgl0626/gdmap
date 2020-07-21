package com.example.gdmap.ui

import com.example.gdmap.database.*
import com.example.gdmap.ui.service.*
import com.example.gdmap.utils.NetWorkUtils
import com.example.gdmap.utils.LogUtils
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

object Repository {
    val videoService = NetWorkUtils.create(VideoService::class.java)
    val articleService = NetWorkUtils.create(ArticleService::class.java)
    val robotService = NetWorkUtils.create(RobotService::class.java)
    val constellationService = NetWorkUtils.create(ConstellationService::class.java)
    val commentService = NetWorkUtils.create(CommentsService::class.java)
    val historyService = NetWorkUtils.create(HistoryService::class.java)
    val jokeService = NetWorkUtils.create(JokeService::class.java)


    fun getArticleData(getArticleData: GetArticleData) {
        articleService?.getArticleData()
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(Schedulers.io())
            ?.subscribeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<ArticleDataBase> {
                override fun onError(e: Throwable?) {
                }

                override fun onNext(t: ArticleDataBase?) {
                    LogUtils.log_d<String>("1" + t?.result?.data.toString())
                    t?.result?.data?.let { getArticleData.success(it) }
                }

                override fun onCompleted() {
                }

            })
    }

    fun getVideoSource(data: GetVideoSource) {
        videoService!!.getVideoSource()
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<VideoDataBase> {
                override fun onError(e: Throwable?) {
                    e?.printStackTrace()
                }

                override fun onNext(t: VideoDataBase?) {
                    t?.result?.let { data.success(it) }
                }

                override fun onCompleted() {
                }

            })
    }

    fun getRobotData(question: String, data: GetRobotData) {
        robotService!!.getRobotData(question)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<RobotDataBase> {
                override fun onError(e: Throwable?) {
                    e?.printStackTrace()
                }

                override fun onNext(t: RobotDataBase?) {
                    if (t != null) {
                        LogUtils.log_d<String>(t.newslist)
                        data.success(t)
                    }
                }

                override fun onCompleted() {
                }
            })
    }

    fun getHistory(date: String, data: GetPlayData) {
        historyService!!.getHistoryData(date)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<PlayDataBase> {
                override fun onError(e: Throwable?) {

                }

                override fun onNext(t: PlayDataBase?) {
                    if (t != null) {
                        data.success(t)
                    } else {
                        LogUtils.log_d<String>("error")
                    }
                }

                override fun onCompleted() {
                }
            })
    }

    fun getComments(data: GetPlayData) {
        commentService!!.getCommentsData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<PlayDataBase> {
                override fun onError(e: Throwable?) {

                }

                override fun onNext(t: PlayDataBase?) {
                    if (t != null) {
                        data.success(t)
                    }
                }

                override fun onCompleted() {
                }
            })
    }

    fun getJoke(data: GetPlayData) {
        jokeService!!.getJokeData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<PlayDataBase> {
                override fun onError(e: Throwable?) {

                }

                override fun onNext(t: PlayDataBase?) {
                    if (t != null) {
                        data.success(t)
                    }
                }

                override fun onCompleted() {
                }
            })
    }

    fun getConstellation(me: String, data: GetPlayData) {
        constellationService!!.getConstellationData(me)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<PlayDataBase> {
                override fun onError(e: Throwable?) {

                }

                override fun onNext(t: PlayDataBase?) {
                    if (t != null) {
                        data.success(t)
                    }
                }

                override fun onCompleted() {
                }
            })
    }
}
