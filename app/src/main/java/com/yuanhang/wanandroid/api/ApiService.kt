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
                                   @Query("page_size")pageSize: Int = 10): JsonResponse<CommonPage<Article>>

    @POST("/article/query/{pageIndex}/json")
    @FormUrlEncoded
    suspend fun searchArticle(@Field("k") keyWord: String,
                              @Path("pageIndex") pageIndex: Int,
                              @Field("page_size") pageSize: Int = 10): JsonResponse<CommonPage<Article>>

    @GET("/article/list/{pageIndex}/json")
    suspend fun getArticleInLevel(@Path("pageIndex") pageIndex: Int,
                                  @Query("cid") levelId: Int,
                                  @Query("page_size") pageSize: Int = 10): JsonResponse<CommonPage<Article>>

    @GET("/article/top/json")
    suspend fun getHomePageTopArticle(): JsonResponse<List<Article>>

    @GET("/hotkey/json")
    suspend fun getSearchHottestWord(): JsonResponse<List<HottestWord>>

    @GET("/tree/json")
    suspend fun getKnowledgeSystem(): JsonResponse<List<Level>>

    @GET("/friend/json")
    suspend fun getUsefulWebsite(): JsonResponse<List<UsefulWebsite>>

    @GET("/navi/json")
    suspend fun getNavigationItem(): JsonResponse<List<NavigationItem>>

    @GET("/project/tree/json")
    suspend fun getProjectKinds(): JsonResponse<List<Level>>

    @GET("/project/list/{pageIndex}/json")
    suspend fun getProjects(@Path("pageIndex") pageIndex: Int,
                            @Query("cid") cid: Int,
                            @Query("page_size") pageSize: Int = 10): JsonResponse<CommonPage<Project>>

    @GET("/user_article/list/{pageIndex}/json")
    suspend fun getKnowledgeSquareArticle(@Path("pageIndex") pageIndex: Int,
                                          @Query("page_size") pageSize: Int = 10): JsonResponse<CommonPage<Article>>

    @POST("/lg/collect/{linkArticleId}/json")
    suspend fun collectInternalArticle(@Path("linkArticleId") articleId: Int): JsonResponse<Any>

    @POST("/lg/collect/add/json")
    @FormUrlEncoded
    suspend fun collectArticle( @Field("title") title: String,
                                @Field("author") author: String,
                                @Field("link") link: String ): JsonResponse<Any>
}