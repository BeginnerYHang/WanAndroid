package com.yuanhang.wanandroid.util

import android.content.Context
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.IOException

/**
 * created by yuanhang on 2022/2/16
 * description:
 */
fun getScreenWidth(context: Context) = context.resources.displayMetrics.widthPixels

fun dp2px(context: Context, dp: Int): Int = QMUIDisplayHelper.dp2px(context, dp)

fun px2dp(context: Context, px: Int): Int = QMUIDisplayHelper.px2dp(context, px)

fun sp2px(context: Context, sp: Int): Int = QMUIDisplayHelper.sp2px(context, sp)

// 指定T为非空类型
fun <T: Any> jsonDumps(obj: T?): String? {
    if (obj == null) return null
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val jsonAdapter = moshi.adapter<T>(obj::class.java)
    return jsonAdapter.toJson(obj)
}

fun <T: Any> jsonListDumps(obj: T?): String? {
    if (obj == null) return null
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val jsonAdapter = moshi.adapter<T>(List::class.java)
    return jsonAdapter.toJson(obj)
}

fun <T> jsonLoads(jsonString: String?, clazz: Class<T>): T? {
    if (jsonString.isNullOrEmpty()) return null
    val moshi = Moshi.Builder().build()
    val jsonAdapter = moshi.adapter(clazz)
    return try {
        jsonAdapter.fromJson(jsonString)
    } catch (e: IOException) {
        null
    }
}

fun <T> jsonListLoads(jsonString: String?, clazz: Class<T>): List<T>? {
    if (jsonString.isNullOrEmpty()) return null
    val listType = Types.newParameterizedType(List::class.java, clazz)
    val moshi = Moshi.Builder().build()
    val jsonAdapter = moshi.adapter<List<T>>(listType)
    return try {
        jsonAdapter.fromJson(jsonString)
    } catch (e: IOException) {
        null
    }
}