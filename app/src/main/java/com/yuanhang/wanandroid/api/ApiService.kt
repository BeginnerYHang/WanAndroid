package com.yuanhang.wanandroid.api

import com.yuanhang.wanandroid.model.*
import retrofit2.http.*

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

    @GET("/article/list/{pageIndex}/json")
    suspend fun getHomePageArticle(@Path("pageIndex")pageIndex: Int,
                                   @Query("page_size")pageSize: Int = 10): JsonResponse<ArticlePage>

    @POST("/article/query/{pageIndex}/json")
    @FormUrlEncoded
    suspend fun searchArticle(@Field("k") keyWord: String,
                              @Path("pageIndex")pageIndex: Int,
                              @Query("page_size")pageSize: Int = 10): JsonResponse<ArticlePage>

    @GET("/article/top/json")
    suspend fun getHomePageTopArticle(): JsonResponse<List<Article>>

    @GET("/hotkey/json")
    suspend fun getSearchHottestWord(): JsonResponse<List<HottestWord>>
}