package com.yuanhang.wanandroid.model

import com.squareup.moshi.JsonClass

/**
 * created by yuanhang on 2022/2/11
 * description:
 */
@JsonClass(generateAdapter = true)
data class AllUserInfo(
    val coinInfo: CoinInfo,
    val userInfo: UserInfo
)

@JsonClass(generateAdapter = true)
data class CoinInfo(
    val coinCount: Int,
    val level: Int,
    val nickname: String,
    val rank: String,
    val userId: Int,
    val username: String
)

@JsonClass(generateAdapter = true)
data class UserInfo(
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