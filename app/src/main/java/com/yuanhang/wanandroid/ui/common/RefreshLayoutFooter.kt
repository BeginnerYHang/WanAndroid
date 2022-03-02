package com.yuanhang.wanandroid.ui.common

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import com.scwang.smart.refresh.layout.simple.SimpleComponent
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.util.dp2px
import com.yuanhang.wanandroid.util.gone
import com.yuanhang.wanandroid.util.visible

/**
 * created by yuanhang on 2022/2/28
 * description: SmartRefreshLayout 自定义Footer
 */
class RefreshLayoutFooter(context: Context, attributeSet: AttributeSet): SimpleComponent(
    context, attributeSet, 0), RefreshFooter {

    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    init {
        initView(context)
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        stopProgressAnim()
        return super.onFinish(refreshLayout, success)
    }

    override fun setNoMoreData(noMoreData: Boolean): Boolean {
        if (noMoreData) {
            progressBar.gone()
            textView.visible()
            textView.text = context.getString(R.string.refresh_no_more_data_tip)
        } else {
            progressBar.visible()
            textView.gone()
        }
        return true
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        super.onStartAnimator(refreshLayout, height, maxDragHeight)
        startProgressAnim()
    }

    private fun initView(context: Context) {
        textView = TextView(context)
        val textViewLayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        textViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        textView.apply {
            textSize = 13f
            setTextColor(Color.parseColor("#4D000000"))
            gravity = Gravity.CENTER
            visibility = View.INVISIBLE
        }
        addView(textView, textViewLayoutParams)
        progressBar = ProgressBar(context)
        val progressBarParams = LayoutParams(dp2px(context, 24), dp2px(context, 24))
        progressBarParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        addView(progressBar, progressBarParams)
        progressBar.indeterminateTintList = ColorStateList.valueOf(Color.GRAY)
        stopProgressAnim()
    }

    private fun startProgressAnim() {
        val drawable = AppCompatResources.getDrawable(context, R.drawable.ic_refresh_loading)
        drawable!!.setBounds(0, 0, dp2px(context,24), dp2px(context, 24))
        progressBar.indeterminateDrawable = drawable
    }

    private fun stopProgressAnim() {
        val drawable = AppCompatResources.getDrawable(context, R.drawable.ic_loading)
        drawable!!.setBounds(0,0, dp2px(context, 24), dp2px(context, 24))
        progressBar.indeterminateDrawable = drawable
    }
}