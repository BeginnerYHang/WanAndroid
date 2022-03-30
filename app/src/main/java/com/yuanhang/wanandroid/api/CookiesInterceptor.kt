package com.yuanhang.wanandroid.api

import com.yuanhang.wanandroid.WanAndroidApplication
import com.yuanhang.wanandroid.util.SPUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * created by yuanhang on 2022/2/16
 * description:
 */
const val COOKIE_HEADER = "Set-Cookie"

class SaveCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        //登录过期需要重新登录
        if (originalResponse.headers(COOKIE_HEADER).isNotEmpty()) {
            val hashSet = SPUtils.StringSet()
            for (cookie in originalResponse.headers(COOKIE_HEADER)) {
                hashSet.add(cookie)
            }
            SPUtils.put(WanAndroidApplication.app, SPUtils.KEY.COOKIES, hashSet)
        }
        return originalResponse
    }

    companion object {
        private const val TOKEN_EXPIRED_CODE = -1001
    }
}

class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val cookies =
            SPUtils.get(WanAndroidApplication.app, SPUtils.KEY.COOKIES, SPUtils.StringSet())
        if (cookies.isNotEmpty()) {
            for (cookie in cookies) {
                requestBuilder.addHeader("Cookie", cookie)
            }
        }
        return chain.proceed(requestBuilder.build())
    }
}