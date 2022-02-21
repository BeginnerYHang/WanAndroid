package com.yuanhang.wanandroid.ui.homepage

import androidx.lifecycle.MutableLiveData
import com.yuanhang.wanandroid.api.ApiService
import com.yuanhang.wanandroid.api.JsonResponse
import com.yuanhang.wanandroid.api.Resource
import com.yuanhang.wanandroid.base.BaseViewModel
import com.yuanhang.wanandroid.model.Article
import com.yuanhang.wanandroid.model.ArticlePage
import com.yuanhang.wanandroid.model.BannerItem
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * created by yuanhang on 2022/2/18
 * description:
 */
class HomePageViewModel @Inject constructor(private val mApi: ApiService) : BaseViewModel() {

    fun getHomePageBanner() = MutableLiveData<Resource<List<BannerItem>>>().apply {
        value = Resource.loading()
        mUiScope.launch {
            value = try {
                val response = mApi.getHomePageBanner()
                Resource.success(response.data, response.errorMsg, response.errorCode)
            } catch (e: Exception) {
                Resource.error(e.message?: "")
            }
        }
    }

    /**
     * TODO:获取首页文章列表,包含置顶文章和一般文章
     * @return
     */
    fun getAllArticle(isRefresh: Boolean, pagIndex: Int) = MutableLiveData<Resource<ArticlePage>>().apply {
        value = Resource.loading()
        mUiScope.launch {
            value = try {
                val allArticle = ArrayList<Article>()
                val topArticle: JsonResponse<List<Article>>
                if (isRefresh) {
                    topArticle = mApi.getHomePageTopArticle()
                    topArticle.data?.let {
                        it.forEach {
                            it.isTop = true
                        }
                        allArticle.addAll(it)
                    }
                }
                val homePageArticle = mApi.getHomePageArticle(pagIndex)
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