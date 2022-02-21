package com.yuanhang.wanandroid.base

import android.content.Context
import android.os.SystemClock
import android.text.TextUtils
import android.util.LruCache
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.util.gone

/**
 * created by yuanhang on 2022/2/15
 * description:
 */
class CustomToastImpl(private val mContext: Context) : CustomToast {
    override fun show(message: String, iconId: Int?, duration: Int) {
        if (TextUtils.isEmpty(message)) {
            return
        }
        TOAST_MAP[message]?.let {
            if (SystemClock.elapsedRealtime() - it < 2000) {
                return
            }
        }
        val customToastView = LayoutInflater.from(mContext).inflate(R.layout.toast_custom, null)
        val ivIcon = customToastView.findViewById<ImageView>(R.id.ivIcon)
        val tvMessage = customToastView.findViewById<TextView>(R.id.tvMessage)
        val customToast = Toast(mContext)
        customToast.view = customToastView
        customToast.setGravity(Gravity.CENTER, 0, 0)
        if (iconId == null) {
            ivIcon.gone()
        } else {
            ivIcon.setImageResource(iconId)
        }
        tvMessage.text = message
        customToast.duration = duration
        customToast.show()
        TOAST_MAP.put(message, SystemClock.elapsedRealtime())
    }

    companion object {
        val TOAST_MAP = LruCache<String, Long>(10)
    }
}