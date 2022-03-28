package com.yuanhang.wanandroid.util

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer

/**
 * created by yuanhang on 2022/3/28
 * description:
 */
class LoggingInterceptor(val isLogRequestBody: Boolean = true): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        var requestLog = "\nSending request ${request.url()} on ${chain.connection()}\n${request.headers()}"
        if (isLogRequestBody) {
            var requestBody = bodyToString(request)
            if (requestBody.isEmpty()) {
                requestBody = "null"
            }
            requestLog += "REQUEST BODY:\n$requestBody"
        }
        Log.d(TAG, requestLog)
        val response = chain.proceed(request)
        val responseCode = response.code()
        val responseBody = response.body()
        val responseBodyStr = responseBody?.string()
        val newResponse = response.newBuilder()
            .body(ResponseBody.create(responseBody?.contentType(), responseBodyStr?: ""))
            .build()

        val t2 = System.nanoTime()

        val responseLog = String.format("\nReceived response for %s in %.1fms code: %s %n%s" +
                "RESPONSE BODY:\n%s",
            response.request().url(),
            (t2 - t1) / 1e6,
            responseCode,
            response.headers(),
            responseBodyStr)
        Log.d(TAG, responseLog)
        return newResponse
    }

    private fun bodyToString(request: Request): String {
        try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()?.writeTo(buffer)
            return buffer.readUtf8()
        } catch (e: Exception) {
            return "don't work"
        }
    }

    companion object {
        private const val TAG = "wanandroid_api_log"
    }
}