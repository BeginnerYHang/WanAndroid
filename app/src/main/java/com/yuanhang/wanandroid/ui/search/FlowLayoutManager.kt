package com.yuanhang.wanandroid.ui.search

import androidx.recyclerview.widget.RecyclerView

/**
 * created by yuanhang on 2022/2/21
 * description:自定义流式布局以显示最经常搜索的热词
 */
class FlowLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
    }
}