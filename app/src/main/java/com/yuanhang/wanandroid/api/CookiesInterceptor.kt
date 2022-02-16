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
class SaveCookiesInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers(COOKIE_HEADER).isNotEmpty()) {
            val hashSet = SPUtils.StringSet()
            for (cookie in originalResponse.headers(COOKIE_HEADER)) {
                hashSet.add(cookie)
            }
            SPUtils.put(WanAndroidApplication.appContext, SPUtils.KEY.COOKIES, hashSet)
        }
        return originalResponse
    }
}

class AddCookiesInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val cookies = SPUtils.get(WanAndroidApplication.appContext, SPUtils.KEY.COOKIES, SPUtils.StringSet())
        if (cookies.isNotEmpty()) {
            for (cookie in cookies) {
                requestBuilder.addHeader("Cookie", cookie)
            }
        }
        return chain.proceed(requestBuilder.build())
    }

}