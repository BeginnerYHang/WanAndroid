package com.yuanhang.wanandroid.di

import com.yuanhang.wanandroid.api.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    companion object {
        val BASE_URL = "https://www.wanandroid.com"
    }
}