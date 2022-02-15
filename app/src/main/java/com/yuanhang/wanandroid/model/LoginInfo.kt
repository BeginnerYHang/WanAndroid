package com.yuanhang.wanandroid.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * created by yuanhang on 2022/2/11
 * description:
 */
@JsonClass(generateAdapter = true)
data class LoginInfo(
    @Json(name = "admin") val admin: Boolean,
    @Json(name = "chapterTops") val chapterTops: List<Int>,
    @Json(name = "coinCount") val coinCount: Int,
    @Json(name = "collectIds") val collectIds: List<Int>,
    @Json(name = "email") val email: String,
    @Json(name = "icon") val icon: String,
    @Json(name = "id") val id: Int,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "password") val password: String,
    @Json(name = "publicName") val publicName: String,
    @Json(name = "token") val token: String,
    @Json(name = "type") val type: Int,
    @Json(name = "username") val username: String
)