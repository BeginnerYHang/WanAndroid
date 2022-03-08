package com.yuanhang.wanandroid.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * created by yuanhang on 2022/3/2
 * description:体系标签(目前为两级,即children嵌套一次)
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class Level (
    val children: List<Level>? = null,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean? = null,
    val visible: Int
) : Parcelable