package com.yuanhang.wanandroid.ui.common

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import androidx.core.widget.addTextChangedListener
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.util.onClick
import kotlinx.android.synthetic.main.dialog_common_tips.*
import kotlinx.android.synthetic.main.layout_form_dialog.*
import kotlinx.android.synthetic.main.layout_form_dialog.btnCancel
import javax.inject.Inject

/**
 * created by yuanhang on 2022/3/28
 * description:
 */
class FormTipsDialog(context: Context,
                     onSuccessCallback: (String, String) -> Unit,
                     cancelable: Boolean = false) : AppCompatDialog(
    context, R.style.QMUI_Dialog
) {

    init {
        setContentView(R.layout.layout_form_dialog)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setWindowAnimations(R.style.FormDialogAnim)
        btnCancel.onClick {
            dismiss()
        }
        if (etArticleTitle.text.toString().isEmpty() or etArticleLink.text.toString().isEmpty()) {
            setShareArticleStatus()
        }
        etArticleTitle.addTextChangedListener {
            setShareArticleStatus()
        }
        etArticleLink.addTextChangedListener {
            setShareArticleStatus()
        }
        setCancelable(cancelable)
        btnShare.onClick {
            val articleTitle = etArticleTitle.text.toString()
            val articleLink = etArticleLink.text.toString()
            onSuccessCallback.invoke(articleTitle, articleLink)
        }
    }

    private fun setShareArticleStatus() {
        btnShare.isEnabled =
            !(etArticleTitle.text.toString().isEmpty() or etArticleLink.text.toString().isEmpty())
    }
}