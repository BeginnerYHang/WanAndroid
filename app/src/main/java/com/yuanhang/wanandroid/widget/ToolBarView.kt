package com.yuanhang.wanandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.util.inVisible
import kotlinx.android.synthetic.main.layout_toolbar.view.*

/**
 * created by yuanhang on 2022/2/17
 * description:
 */
class ToolBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1,
    defStyleRes: Int = -1
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val toolBarView: View =
        LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this, true)

    init {
        val obtainAttrs = context.theme.obtainStyledAttributes(attrs, R.styleable.ToolBarView, 0, 0)
        val leftIconId: Int
        val rightIconId: Int
        val mediumText: String?
        val mediumTextGravity: Int
        try {
            leftIconId = obtainAttrs.getResourceId(R.styleable.ToolBarView_leftIcon, -1)
            rightIconId = obtainAttrs.getResourceId(R.styleable.ToolBarView_rightIcon, -1)
            mediumText = obtainAttrs.getString(R.styleable.ToolBarView_mediumTitle)
            mediumTextGravity = obtainAttrs.getInteger(R.styleable.ToolBarView_mediumTitleGravity, -1)
        }finally {
            obtainAttrs.recycle()
        }
        if (leftIconId == -1) {
            toolBarView.leftIcon.inVisible()
        } else {
            toolBarView.leftIcon.setImageResource(leftIconId)
        }
        if (rightIconId == -1) {
            toolBarView.rightIcon.inVisible()
        } else {
            toolBarView.rightIcon.setImageResource(rightIconId)
        }
        mediumText?.let { toolBarView.tvCenter.text = it }
        toolBarView.tvCenter.textAlignment = when(mediumTextGravity) {
            1 -> Gravity.START
            3 -> Gravity.END
            else -> Gravity.CENTER
        }
    }

    fun setRightIcon(@DrawableRes drawableRes: Int) {
        toolBarView.rightIcon.setImageResource(drawableRes)
    }

    fun setMediumText(mediumText: String) {
        toolBarView.tvCenter.text = mediumText
    }
}