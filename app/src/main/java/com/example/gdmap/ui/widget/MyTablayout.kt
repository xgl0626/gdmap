package com.example.gdmap.ui.widget


import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import com.example.gdmap.R
import com.google.android.material.tabs.TabLayout


class MyTabLayout : TabLayout {
    private var titles: List<String>? = null

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        titles = ArrayList()
        this.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab) {
                /**
                 * 设置当前选中的Tab为特殊高亮样式。
                 */
                if (tab.customView != null) {
                    val tab_layout_text: TextView = tab.customView!!.findViewById(R.id.map_tv_label_item)
                    tab_layout_text.setBackgroundResource(R.drawable.shape_et_search_place)
                }

            }

            override fun onTabUnselected(tab: Tab) {
                /**
                 * 重置所有未选中的Tab颜色、字体、背景恢复常态(未选中状态)。
                 */
                if (tab.customView != null) {
                    val tab_layout_text: TextView = tab.customView!!.findViewById(R.id.map_tv_label_item)
                    tab_layout_text.setBackgroundResource(R.drawable.shape_no_select_label_item)
                }

            }

            override fun onTabReselected(tab: Tab) {}
        })
    }

    fun setTitle(titles: ArrayList<String>) {
        this.titles = titles
        /**
         * 开始添加切换的Tab。
         */
        for (title in this.titles!!) {
            val tab = newTab()
            tab.setCustomView(R.layout.item_tablayout_label_one)
            if (tab.customView != null) {
                if (!title.equals(null)) {
                    val text: TextView = tab.customView!!.findViewById(R.id.map_tv_label_item)
                    text.text = title
                }
                this.addTab(tab, false)
            }
        }

    }
}