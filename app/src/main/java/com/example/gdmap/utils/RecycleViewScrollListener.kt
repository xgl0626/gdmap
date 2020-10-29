package com.example.gdmap.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RecycleViewScrollListener() : RecyclerView.OnScrollListener() {
    private var isSildingUpward: Boolean = false
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val manager = recyclerView.layoutManager as LinearLayoutManager
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val lastItemPosition: Int = manager.findLastCompletelyVisibleItemPosition()
            val itemCount: Int = manager.itemCount
            if (lastItemPosition == (itemCount - 1) && isSildingUpward)
                loadMore()
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        isSildingUpward = dy > 0
    }

    abstract fun loadMore()
}