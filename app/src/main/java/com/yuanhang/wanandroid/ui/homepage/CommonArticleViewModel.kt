package com.yuanhang.wanandroid.ui.homepage

import android.hardware.biometrics.BiometricManager
import androidx.lifecycle.MutableLiveData
import com.yuanhang.wanandroid.api.ApiService
import com.yuanhang.wanandroid.api.JsonResponse
import com.yuanhang.wanandroid.api.Resource
import com.yuanhang.wanandroid.base.BaseViewModel
import com.yuanhang.wanandroid.model.Article
import com.yuanhang.wanandroid.model.CommonPage
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * created by yuanhang on 2022/3/2
 * description:
 */
class CommonArticleViewModel @Inject constructor(val mApi: ApiService) : BaseViewModel() {

    /**
     * TODO：配合CommonArticleFragment从服务端请求数据
     * @return
     */
    fun getAllArticle(
        isRefresh: Boolean,
        pageIndex: Int,
        keyWord: String? = null,
        levelId: Int? = null,
        isShare: Boolean? = false
    ) = MutableLiveData<Resource<CommonPage<Article>>>().apply {
        value = Resource.loading()
        mUiScope.launch {
            value = try {
                val allArticle = ArrayList<Article>()
                val homePageArticle: JsonResponse<CommonPage<Article>>
                when {
                    isShare == true -> {
                        homePageArticle = mApi.getKnowledgeSquareArticle(pageIndex)
                    }
                    keyWord != null -> {
                        homePageArticle = mApi.searchArticle(keyWord, pageIndex)
                    }
                    levelId != null -> {
                        homePageArticle = mApi.getArticleInLevel(pageIndex, levelId)
                    }
                    else -> {
                        if (isRefresh) {
                            val topArticle = mApi.getHomePageTopArticle()
                            topArticle.data?.let {
                                it.forEach {
                                    it.isTop = true
                                }
                                allArticle.addAll(it)
                            }
                        }
                        homePageArticle = mApi.getHomePageArticle(pageIndex)
                    }
                }
                homePageArticle.data?.articles?.let {
                    it.forEach {
                        it.isTop = false
                    }
                    allArticle.addAll(it)
                }
                homePageArticle.data?.articles = allArticle
                Resource.success(homePageArticle.data)
            } catch (e: Exception) {
                Resource.error(e.message ?: "")
            }
        }
    }

    fun collectArticle(articleItem: Article) = MutableLiveData<Resource<Any>>().apply {
        var internalArticleId: Int? = null
        if (articleItem.isWanAndroidArticle()) {
            val lastIndexOf = articleItem.link.lastIndexOf('/')
            internalArticleId = articleItem.link.substring(lastIndexOf + 1).toInt()
        }
        mUiScope.launch {
            value = try {
                if (articleItem.isWanAndroidArticle()) {
                    Resource.success(mApi.collectInternalArticle(internalArticleId!!).data)
                } else {
                    Resource.success(mApi.collectArticle(articleItem.title, articleItem.author, articleItem.link))
                }
            } catch (e: Exception) {
                Resource.error(e.message?: "")
            }
        }
    }
}