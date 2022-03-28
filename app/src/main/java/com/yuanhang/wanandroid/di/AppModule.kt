package com.yuanhang.wanandroid.di

import com.yuanhang.wanandroid.api.AddCookiesInterceptor
import com.yuanhang.wanandroid.api.ApiService
import com.yuanhang.wanandroid.api.CustomMoshiConverterFactory
import com.yuanhang.wanandroid.api.SaveCookiesInterceptor
import com.yuanhang.wanandroid.util.LoggingInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * created by yuanhang on 2022/2/10
 * description:
 */
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(SaveCookiesInterceptor())
            .addInterceptor(LoggingInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(CustomMoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    companion object {
        val BASE_URL = "https://www.wanandroid.com"
    }
}