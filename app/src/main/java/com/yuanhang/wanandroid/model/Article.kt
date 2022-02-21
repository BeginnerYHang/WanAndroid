package com.yuanhang.wanandroid.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * created by yuanhang on 2022/2/21
 * description:
 */

@JsonClass(generateAdapter = true)
data class ArticlePage(
    val curPage: Int,
    @Json(name = "datas") var articles: List<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    @Json(name = "size") val pageSize: Int,
    val total: Int
)

@JsonClass(generateAdapter = true)
data class Article(
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Tag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int,
    // 自定义字段,定义文章是否置顶
    var isTop: Boolean?
)