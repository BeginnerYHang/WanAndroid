package com.yuanhang.wanandroid.api

import com.yuanhang.wanandroid.model.BannerItem
import com.yuanhang.wanandroid.model.LoginInfo
import com.yuanhang.wanandroid.model.UserInfo
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * created by yuanhang on 2022/2/10
 * description: 网络请求访问
 */
interface ApiService {

    @POST("/user/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): JsonResponse<LoginInfo>

    @GET("/user/logout/json")
    suspend fun logout(): JsonResponse<Any>

    @GET("/user/lg/userinfo/json")
    suspend fun getUserInfo():  JsonResponse<UserInfo>

    @GET("/banner/json")
    suspend fun getHomePageBanner(): JsonResponse<List<BannerItem>>
}