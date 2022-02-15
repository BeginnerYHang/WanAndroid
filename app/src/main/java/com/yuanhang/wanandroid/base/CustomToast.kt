package com.yuanhang.wanandroid.base

import androidx.annotation.DrawableRes

/**
 * created by yuanhang on 2022/2/15
 * description:
 */
interface CustomToast {
    fun show(message: String, @DrawableRes iconId: Int? = null, )
}