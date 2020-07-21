package com.example.gdmap.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import cn.jzvd.Jzvd
import com.example.gdmap.R
import com.example.gdmap.database.VideoDataBase
import com.example.gdmap.ui.GetVideoSource
import com.example.gdmap.ui.adapter.VideoAdapter
import com.example.gdmap.ui.viewmodel.VideoViewModel
import kotlinx.android.synthetic.main.fragment_video.*


class VideoFragment :Fragment(),View.OnClickListener{
    var videoSourceList=ArrayList<VideoDataBase.Result>()
    val videoViewModel by lazy { ViewModelProviders.of(this).get(VideoViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_video,container,false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser)

        else
        {
            Jzvd.resetAllVideos()
        }
    }
    private fun loadData() {
        videoViewModel.getVideoSource(object :GetVideoSource{

            override fun success(data:List<VideoDataBase.Result>) {
                videoSourceList= data as ArrayList<VideoDataBase.Result>
                sendMsg(0)
            }

            override fun failure() {

            }
        })
    }

    override fun onClick(view: View?) {

    }
    fun sendMsg(index: Int) {
        val message = Message()
        message.what = index
        handler.sendMessage(message)
    }
    private val handler: Handler = object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what==0){
                val layoutManager = LinearLayoutManager(context)
                rv_fragment_article_item_videoview.layoutManager = layoutManager
                val videoAdapter = VideoAdapter(context,videoSourceList)
                rv_fragment_article_item_videoview.adapter=videoAdapter
                videoAdapter.notifyDataSetChanged()
            }
        }

    }
}