package com.arbaelbarca.github_user_project.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnLoadMoreScrollListener(
    val layoutManager: LinearLayoutManager,
    val listener: () -> Unit
) : RecyclerView.OnScrollListener() {
    private val visibleThreshold = 10
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var loading: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) { // scroll down
            totalItemCount = layoutManager.itemCount
            lastVisibleItem = layoutManager
                .findLastVisibleItemPosition()
            if (!recyclerView.canScrollVertically(1)) {
                listener()
                loading = false
            }
        }
    }
}