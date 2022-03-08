package com.yuanhang.wanandroid.model

import com.squareup.moshi.JsonClass

/**
 * created by yuanhang on 2022/3/5
 * description:
 */
@JsonClass(generateAdapter = true)
data class NavigationItem(
    val articles: List<Article>,
    val cid: Int,
    val name: String
)