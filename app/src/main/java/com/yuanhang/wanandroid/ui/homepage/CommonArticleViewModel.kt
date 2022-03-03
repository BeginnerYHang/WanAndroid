package com.yuanhang.wanandroid.ui.homepage

import androidx.lifecycle.MutableLiveData
import com.yuanhang.wanandroid.api.ApiService
import com.yuanhang.wanandroid.api.JsonResponse
import com.yuanhang.wanandroid.api.Resource
import com.yuanhang.wanandroid.base.BaseViewModel
import com.yuanhang.wanandroid.model.Article
import com.yuanhang.wanandroid.model.ArticlePage
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * created by yuanhang on 2022/3/2
 * description:
 */
class CommonArticleViewModel @Inject constructor(val mApi: ApiService): BaseViewModel() {

    /**
     * TODO:获取首页文章列表,包含置顶文章和一般文章
     * @return
     */
    fun getAllArticle(isRefresh: Boolean, pageIndex: Int, keyWord: String? = null, levelId: Int? = null) = MutableLiveData<Resource<ArticlePage>>().apply {
        value = Resource.loading()
        mUiScope.launch {
            value = try {
                val allArticle = ArrayList<Article>()
                if (keyWord == null && levelId == null) {
                    if (isRefresh) {
                        val topArticle = mApi.getHomePageTopArticle()
                        topArticle.data?.let {
                            it.forEach {
                                it.isTop = true
                            }
                            allArticle.addAll(it)
                        }
                    }
                }
                val homePageArticle = if (keyWord == null && levelId == null) {
                    mApi.getHomePageArticle(pageIndex)
                } else if (levelId == null){
                    mApi.searchArticle(keyWord!!, pageIndex)
                } else {
                    mApi.getArticleInLevel(pageIndex, levelId)
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
                Resource.error(e.message?: "")
            }
        }
    }
}