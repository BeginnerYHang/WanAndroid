package com.yuanhang.wanandroid.ui.search

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yuanhang.wanandroid.util.dp2px

/**
 * created by yuanhang on 2022/2/22
 * description:
 */
class HottestWordDecoration: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = dp2px(parent.context, 8)
        outRect.bottom = dp2px(parent.context, 4)
    }
}