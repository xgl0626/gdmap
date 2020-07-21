package com.example.gdmap.utils

import android.content.Context
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.get
import com.amap.api.services.help.Inputtips
import com.amap.api.services.help.InputtipsQuery
import com.amap.api.services.help.Tip
import com.example.gdmap.utils.LogUtils

object InputTipUtils:Inputtips.InputtipsListener  {
    var autoText:AutoCompleteTextView?=null
    var context: Context?=null
    var tiplist:MutableList<Tip>?=null
    fun getTipList():MutableList<Tip>?
    {
        return tiplist
    }
    fun setTipList(tiplist: MutableList<Tip>?) {
        this.tiplist=tiplist
    }
    override fun onGetInputtips(tiplist: MutableList<Tip>?, code: Int) {
        if (code == 1000) {
            val nameList=ArrayList<String>()
            LogUtils.log_d<String>(tiplist.toString())
            if (tiplist != null) {
                setTipList(tiplist)
                for (tip in tiplist) {
                    nameList.add(tip.name)
                }

                autoText?.threshold = 1
                val adapter = context?.let { ArrayAdapter<String>(it,android.R.layout.simple_list_item_1,nameList) }
                autoText?.setAdapter(adapter)
                adapter?.notifyDataSetChanged()
            }
        }
    }
    fun setContext(autoText: AutoCompleteTextView, context: Context?): InputTipUtils {
        this.autoText=autoText
        this.context=context
        return this
    }

     fun initInputTip(newText: String, city: String) {
        val inputtipsQuery = InputtipsQuery(newText, city)
        inputtipsQuery.cityLimit = true
        val inputtips = Inputtips(context, inputtipsQuery)
        inputtips.setInputtipsListener(this)
        inputtips.requestInputtipsAsyn()
    }
}