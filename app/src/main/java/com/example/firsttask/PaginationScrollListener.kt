package com.example.firsttask

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class PaginationScrollListener constructor():
    RecyclerView.OnScrollListener() {
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    constructor(layoutManager: LinearLayoutManager) : this() {
        this.mLayoutManager = layoutManager
    }
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = mLayoutManager.childCount
        val totalItemCount = mLayoutManager.itemCount
        var firstVisibleItemPosition = 0

        when (mLayoutManager) {
            is LinearLayoutManager -> {
                firstVisibleItemPosition =
                        (mLayoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            }
        }
        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()

    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean

    companion object {
        private val TAG = PaginationScrollListener::class.java.simpleName
    }
}
