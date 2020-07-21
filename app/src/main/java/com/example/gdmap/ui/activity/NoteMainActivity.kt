package com.example.gdmap.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.database.NoteDataBase
import com.example.gdmap.database.NotesBook
import com.example.gdmap.ui.adapter.NoteAdapter
import com.example.gdmap.utils.ImmersedStatusbarUtils
import com.example.gdmap.utils.MyApplication
import com.example.gdmap.utils.MyApplication.Companion.context
import kotlinx.android.synthetic.main.acticity_note.*
import kotlinx.android.synthetic.main.acticity_note.toolBar
import kotlin.concurrent.thread

class NoteMainActivity : BaseActivity() {
    private var notedao = NoteDataBase.getDatabase(MyApplication.context).noteDao()
    private var noteAdapter: NoteAdapter? = null
    private var noteList = ArrayList<NotesBook>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acticity_note)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用
        ImmersedStatusbarUtils.initSetContentView(this, toolBar)
        initView()
        initData()
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.acticity_acticle_content_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.it_menu_clear -> {
                thread {
                    for (note in notedao.loadAllNotes()) {
                        notedao?.deleteNotes(note)
                    }
                    noteList.clear()
                }
                noteAdapter?.notifyDataSetChanged()
            }
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {

        val layoutManager = LinearLayoutManager(context)
        rv_activity_notes.layoutManager = layoutManager
        noteAdapter = context?.let { NoteAdapter(it, noteList) }
        rv_activity_notes.adapter = noteAdapter
        fb_activity_note_addnote.setOnClickListener {
            changeToActivity(NoteContentActivity())
        }
    }

    private fun initData() {
        noteList.clear()
        thread {
            for (note in notedao.loadAllNotes()) {
                if (!noteList.contains(note))
                    noteList.add(note)
            }
        }
        noteAdapter?.notifyDataSetChanged()
    }

    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

}