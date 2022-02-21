package com.yuanhang.wanandroid.model

import com.squareup.moshi.JsonClass

/**
 * created by yuanhang on 2022/2/21
 * description:
 */
@JsonClass(generateAdapter = true)
data class Tag(
    val name: String,
    val url: String
)