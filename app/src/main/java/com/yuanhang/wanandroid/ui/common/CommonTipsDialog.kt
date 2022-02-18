package com.yuanhang.wanandroid.ui.common

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.TextUtils
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButtonDrawable
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.util.getScreenWidth
import com.yuanhang.wanandroid.util.gone
import com.yuanhang.wanandroid.util.onClick
import kotlinx.android.synthetic.main.layout_common_tips_dialog.*

/**
 * created by yuanhang on 2022/2/16
 * description:
 */
class CommonTipsDialog constructor(
    activity: BaseActivity,
    titleText: String? = null,
    message: String,
    confirmText: String,
    onConfirm: (() -> Unit)? = null,
    cancelText: String? = null,
    onCancel: (() -> Unit)? = null,
    isCancel: Boolean = false,
    isWarning: Boolean = false
) : AppCompatDialog(activity, R.style.QMUI_Dialog) {
    init {
        setContentView(R.layout.layout_common_tips_dialog)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, getScreenWidth(context))
        if (TextUtils.isEmpty(titleText)) {
            tvTitle.gone()
        } else {
            tvTitle.text = titleText
        }
        tvMessage.text = message
        btnConfirm.text = confirmText
        btnConfirm.onClick {
            onConfirm?.invoke()
            dismiss()
        }
        if (TextUtils.isEmpty(cancelText)) {
            btnCancel.gone()
        } else {
            btnCancel.text = cancelText
            btnCancel.onClick {
                onCancel?.invoke()
                dismiss()
            }
        }
        setCancelable(isCancel)
        if (isWarning) {
            val confirmBackground = btnConfirm.background as QMUIRoundButtonDrawable
            confirmBackground.setBgData(ColorStateList.valueOf(Color.parseColor("#FD504B")))
        }
    }
}