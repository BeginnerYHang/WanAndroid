package com.yuanhang.wanandroid.model

import com.squareup.moshi.JsonClass

/**
 * created by yuanhang on 2022/2/22
 * description:
 */
@JsonClass(generateAdapter = true)
data class HottestWord(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)