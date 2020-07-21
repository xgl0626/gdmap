package com.example.gdmap.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gdmap.R
import com.example.gdmap.ui.activity.*
import com.example.gdmap.utils.ToastUtils.showToast
import kotlinx.android.synthetic.main.fragment_play.*

class PlayFragment : Fragment(),View.OnClickListener{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_play,container,false)
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        bt_play_comments.setOnClickListener(this)
        bt_play_constellation.setOnClickListener(this)
        bt_play_history.setOnClickListener(this)
        bt_play_more.setOnClickListener(this)
        bt_play_note.setOnClickListener(this)
        bt_play_joke.setOnClickListener(this)
        setImageViewAndButton(R.mipmap.activity_play_comment,bt_play_comments,1)
        setImageViewAndButton(R.mipmap.activity_play_history,bt_play_history,1)
        setImageViewAndButton(R.mipmap.activity_play_more,bt_play_more,1)
        setImageViewAndButton(R.mipmap.activity_play_xz,bt_play_constellation,1)
        setImageViewAndButton(R.mipmap.activity_play_smile,bt_play_joke,1)
        setImageViewAndButton(R.mipmap.activity_play_note,bt_play_note,1)
    }


    private fun changeToActivity(point:String,activity: Activity) {
        val intent = Intent(this.context, activity::class.java)
        intent.putExtra("point",point)
        startActivity(intent)
    }
    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this.context, activity::class.java)
        startActivity(intent)
    }

    override fun onClick(view: View?) {
        when(view?.id)
        {
            R.id.bt_play_joke->
            {
                changeToActivity("0",PlayItemActivity())
            }
            R.id.bt_play_comments->
            {
                changeToActivity("1",PlayItemActivity())

            }
            R.id.bt_play_history->
            {
                changeToActivity(HistoryActivity())
            }
            R.id.bt_play_note->{
                changeToActivity(NoteMainActivity())
            }
            R.id.bt_play_constellation->
            {
                changeToActivity(ContentllationActivity())
            }
            R.id.bt_play_more->{
                "没有更多了".showToast()
            }
        }
    }
    private fun setImageViewAndButton(drawable: Int, view: TextView, id: Int) {
        val drawable: Drawable =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                resources.getDrawable(drawable, null)
            } else {
                TODO("VERSION.SDK_INT < LOLLIPOP")
            }
        drawable.setBounds(0, 0, 120, 120)
        when (id) {
            1 -> view.setCompoundDrawables(null, drawable, null, null)
        }
    }
}