package com.yuanhang.wanandroid.ui.my

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuanhang.wanandroid.api.ApiService
import com.yuanhang.wanandroid.api.COOKIE_HEADER
import com.yuanhang.wanandroid.api.Resource
import com.yuanhang.wanandroid.base.BaseViewModel
import com.yuanhang.wanandroid.model.ShareArticleList
import com.yuanhang.wanandroid.util.SPUtils
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * created by yuanhang on 2022/2/21
 * description:
 */
class UserInfoViewModel @Inject constructor(private val mApi: ApiService): BaseViewModel() {

    var userId: Int? = null
    var isGuest: Boolean = false
    var pageIndex: Int = 1

    fun getShareArticle(userId: Int, isGuest: Boolean) = MutableLiveData<Resource<ShareArticleList>>().apply {
        mUiScope.launch {
            value = try {
                if (isGuest) {
                    Resource.success(mApi.getShareArticle(pageIndex, userId).data)
                } else {
                    Resource.success(mApi.getMySelfShareArticle(pageIndex).data)
                }
            }catch (e: Exception) {
                Resource.error(e.message?: "")
            }
        }
    }

    fun logout() = MutableLiveData<Resource<Any>>().apply {
        value = Resource.loading()
        mUiScope.launch {
            val jsonResponse = mApi.logout()
            value = try {
                Resource.success(data = jsonResponse.data, code = jsonResponse.errorCode)
            }catch (e: Exception) {
                Resource.error(e.message ?: "")
            }
        }
    }
}