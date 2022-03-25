package com.yuanhang.wanandroid.ui.common

import android.graphics.Color
import android.os.SystemClock
import android.view.View
import com.qmuiteam.qmui.span.QMUITouchableSpan

/**
 * created by yuanhang on 2022/3/3
 * description:
 */
class NicknameClickSpan(val click: () -> Unit): QMUITouchableSpan(
    Color.parseColor("#FFCBA0FF"),
    Color.parseColor("#FFCBA0FF"),
    Color.parseColor("#00FFFFFF"),
    Color.parseColor("#00FFFFFF")
) {
    private var previousClickTime = 0L

    override fun onSpanClick(widget: View?) {
        val now = SystemClock.elapsedRealtime()
        if (now - previousClickTime < 300) {
            return
        }
        previousClickTime = now
        click.invoke()
    }
}