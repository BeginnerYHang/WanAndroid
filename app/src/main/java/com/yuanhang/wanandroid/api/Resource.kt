package com.yuanhang.wanandroid.api

/**
 * created by yuanhang on 2022/2/11
 * description:
 */
data class Resource<out T> (
    val status: Status,
    val data: T?,
    val message: String?,
    val code: Int = -1){
    companion object {
        fun <T> success(data: T? = null, message: String? = null) = Resource<T>(Status.SUCCESS, data, message)

        fun <T> error(message: String, data: T? = null, code: Int = -1) = Resource<T>(Status.ERROR, data, message, code)

        fun <T> loading(data: T? = null) = Resource<T>(Status.LOADING, data = data, null)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}