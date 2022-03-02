package com.yuanhang.wanandroid.ui.search

import android.graphics.Rect
import android.util.Log
import android.util.SparseArray
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

/**
 * created by yuanhang on 2022/2/21
 * description:自定义流式布局以显示最经常搜索的热词(目前仅考虑了itemView宽度不同,高度一致且水平方向填满的情况)
 */
class FlowLayoutManager() : RecyclerView.LayoutManager() {

    //初始化时暂存itemView的位置信息
    private val mRects = SparseArray<Rect>()

    //水平方向填满的流式布局,计算整个所需高度
    private var mTotalHeight = paddingTop

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (itemCount <= 0) {
            detachAndScrapAttachedViews(recycler)
            return
        }
//        detachAndScrapAttachedViews(recycler)
        //从RecyclerView的paddingStart位置作为宽度的起始位置
        var startWidth = paddingStart
        //记录初始化需要添加的view数
        for (i in 0 until itemCount) {
            val itemView = recycler.getViewForPosition(i)
            measureChildWithMargins(itemView, 0, 0)
            val itemWidth = getDecoratedMeasuredWidth(itemView)
            val itemHeight = getDecoratedMeasuredHeight(itemView)
            if (i == 0) {
                mTotalHeight += itemHeight
            }
            if (startWidth + itemWidth <= paddingStart + width) {
                // 说明剩余空间足够摆放该View
                mRects.put(i, Rect(startWidth, mTotalHeight - itemHeight, startWidth + itemWidth, mTotalHeight))
                startWidth += itemWidth
            } else {
                // 该行空间放不下该View,转到下一行
                startWidth = paddingStart
                mRects.put(i, Rect(startWidth, mTotalHeight, startWidth + itemWidth, mTotalHeight + itemHeight))
                startWidth += itemWidth
                mTotalHeight += itemHeight
            }
        }
        Log.d("???", "onLayoutChildren: $childCount")
        //布局初始化的一屏itemView
        for (i in 0 until itemCount) {
            val rect = mRects[i]
            val view = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            layoutDecorated(view, rect.left, rect.top, rect.right, rect.bottom)
        }
    }
}