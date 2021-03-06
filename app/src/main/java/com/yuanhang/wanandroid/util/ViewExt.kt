package com.yuanhang.wanandroid.util

import android.os.SystemClock
import android.view.View

/**
 * created by yuanhang on 2022/2/11
 * description:
 */
fun View.onClick(debounceInterval: Int = 300, action: (View) -> Unit) {
    val listener = object : OnDebouncedClickListener(debounceInterval) {
        override fun onViewClick(v: View) {
            action.invoke(v)
        }
    }
    setOnClickListener(listener)
}

fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.inVisible() {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
}

abstract class OnDebouncedClickListener(private val interval: Int) : View.OnClickListener {
    private var lastClickTime = 0L
    override fun onClick(v: View) {
        val now = SystemClock.elapsedRealtime()
        if (now - lastClickTime > interval) {
            onViewClick(v)
            lastClickTime = now
        }
    }

    abstract fun onViewClick(v: View)
}