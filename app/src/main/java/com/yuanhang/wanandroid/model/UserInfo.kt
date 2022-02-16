package com.yuanhang.wanandroid.model

import com.squareup.moshi.JsonClass

/**
 * created by yuanhang on 2022/2/11
 * description:
 */
@JsonClass(generateAdapter = true)
data class UserInfo(
    val coinInfo: CoinInfo,
    val userInfo: LoginInfo
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

