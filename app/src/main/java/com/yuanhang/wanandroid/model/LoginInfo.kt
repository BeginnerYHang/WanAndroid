package com.yuanhang.wanandroid.model

import com.squareup.moshi.JsonClass

/**
 * created by yuanhang on 2022/2/16
 * description:
 */
@JsonClass(generateAdapter = true)
data class LoginInfo(
    val admin: Boolean,
    val chapterTops: List<Int>,
    val coinCount: Int,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)