package com.example.gdmap.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.MenuItem
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.database.NoteDao
import com.example.gdmap.database.NoteDataBase
import com.example.gdmap.database.NotesBook
import com.example.gdmap.ui.adapter.NoteAdapter
import com.example.gdmap.utils.ImmersedStatusbarUtils
import com.example.gdmap.utils.LogUtils
import com.example.gdmap.utils.MyApplication.Companion.context
import kotlinx.android.synthetic.main.activity_article_content.toolBar
import kotlinx.android.synthetic.main.activity_note_content.*
import kotlin.concurrent.thread

class NoteContentActivity :BaseActivity()
{
    private var noteDao:NoteDao?=null
    private var noteTime:String?=null
    private var updateNote:NotesBook?=null
    private var point=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_content)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用
        ImmersedStatusbarUtils.initSetContentView(this,toolBar)
        init()
        initData()
    }

    override fun onStart() {
        super.onStart()
        initData()
    }
    private fun initData() {
        val intent=intent
        val id=intent.getLongExtra("id",0)
        point=intent.getIntExtra("point",0)
       thread { updateNote= noteDao?.getNotesBookByName(id)}
        et_activity_note_content.setText(updateNote?.content)
        et_activity_note_title.setText(updateNote?.title)
        tv_activity_note_time.text = updateNote?.time
    }


    private fun init() {
        val time = android.text.format.Time()
        time.setToNow()
        val day = time.monthDay.toString()
        val month=(time.month+1).toString()
        val year=time.year.toString()
        noteTime=year+"-"+month+"-"+day
        sendMsg(0)
        noteDao=NoteDataBase.getDatabase(this).noteDao()
        fb_activity_note_content_finishnote.setOnClickListener {
            if (point==1)
            {
                updateNote?.time=noteTime.toString()
                updateNote?.title=et_activity_note_title.text.toString()
                updateNote?.content=et_activity_note_content.text.toString()
               thread { updateNote?.let { it1 -> noteDao!!.updateNotes(it1) }}
                finish()
            }
            else {
                val note = NotesBook(
                    et_activity_note_title.text.toString(),
                    et_activity_note_content.text.toString(),
                    noteTime!!
                )
                thread {
                    noteDao!!.insertNotes(note)
                }
                finish()
            }
        }
    }
    fun sendMsg(index: Int) {
        val message = Message()
        message.what = index
        handler.sendMessage(message)
    }
    private val handler: Handler =
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what==0){
                tv_activity_note_time.text = noteTime
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home)
        {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

