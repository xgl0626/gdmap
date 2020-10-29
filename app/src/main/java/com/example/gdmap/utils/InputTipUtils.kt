package com.example.gdmap.utils

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.amap.api.services.help.Inputtips
import com.amap.api.services.help.InputtipsQuery
import com.amap.api.services.help.Tip

object InputTipUtils : Inputtips.InputtipsListener {
    var autoText: AutoCompleteTextView? = null
    var context: Context? = null
    var tiplist: MutableList<Tip>? = null
    fun getTipList(): MutableList<Tip>? {
        return tiplist
    }

    private fun setTipList(tipList: MutableList<Tip>?) {
        this.tiplist = tipList
    }

    override fun onGetInputtips(tiplist: MutableList<Tip>?, code: Int) {
        if (code == 1000) {
            val nameList = ArrayList<String>()
            LogUtils.log_d<String>(tiplist.toString())
            if (tiplist != null) {
                setTipList(tiplist)
                for (tip in tiplist) {
                    nameList.add(tip.name)
                }

                autoText?.threshold = 1
                val adapter = context?.let {
                    ArrayAdapter<String>(
                        it,
                        android.R.layout.simple_list_item_1,
                        nameList
                    )
                }
                autoText?.setAdapter(adapter)
                adapter?.notifyDataSetChanged()
            }
        }
    }

    fun setContext(autoText: AutoCompleteTextView, context: Context?): InputTipUtils {
        this.autoText = autoText
        this.context = context
        return this
    }

    fun initInputTip(newText: String, city: String) {
        val inputTipsQuery = InputtipsQuery(newText, city)
        inputTipsQuery.cityLimit = true
        val inputTips = Inputtips(context, inputTipsQuery)
        inputTips.setInputtipsListener(this)
        inputTips.requestInputtipsAsyn()
    }
}