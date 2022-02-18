package com.yuanhang.wanandroid.ui.homepage

import androidx.lifecycle.MutableLiveData
import com.yuanhang.wanandroid.api.ApiService
import com.yuanhang.wanandroid.api.Resource
import com.yuanhang.wanandroid.base.BaseViewModel
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
}