package com.yuanhang.wanandroid.util

import android.content.Context

/**
 * created by yuanhang on 2022/2/16
 * description:
 */
object SPUtils {
    //SPUtils保存在手机上的文件名称
    private const val FILE_NAME = "wanandroid"

    fun put(context: Context, key: String, obj: Any) {
        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        when(obj) {
            is Int -> editor.putInt(key, obj)
            is Boolean -> editor.putBoolean(key, obj)
            is Float -> editor.putFloat(key, obj)
            is Long -> editor.putLong(key, obj)
            is StringSet -> editor.putStringSet(key, obj)
            else -> editor.putString(key, obj.toString())
        }
        editor.apply()
    }

    fun <T: Any> get(context: Context, key: String, defaultObj: T): T {
        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val value = when(defaultObj) {
            is Int -> sp.getInt(key, defaultObj)
            is Boolean -> sp.getBoolean(key, defaultObj)
            is Float -> sp.getFloat(key, defaultObj)
            is Long -> sp.getLong(key, defaultObj)
            is StringSet -> StringSet(sp.getStringSet(key, defaultObj) as HashSet<String>)
            else -> sp.getString(key, defaultObj.toString())
        }
        return value as T
    }

    class StringSet: HashSet<String> {
        constructor(): super()
        constructor(collection: Collection<String>): super(collection)
    }

    object KEY {
        const val LOGIN_INFO = "login_info"
        const val USER_INFO = "user_info"
        const val COOKIES = "cookies"
    }
}