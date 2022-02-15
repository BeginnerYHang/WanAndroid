package com.yuanhang.wanandroid.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * created by Yuan Hang on 2022/2/10
 * description:
 */
@JsonClass(generateAdapter = true)
data class JsonResponse<T>(
    @Json(name = "data") val data: T? = null,
    @Json(name = "errorCode") val errorCode: Int,
    @Json(name = "errorMsg") val errorMsg: String
)