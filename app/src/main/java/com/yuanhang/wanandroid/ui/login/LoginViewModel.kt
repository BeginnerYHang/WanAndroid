package com.yuanhang.wanandroid.ui.login

import androidx.lifecycle.MutableLiveData
import com.yuanhang.wanandroid.api.ApiService
import com.yuanhang.wanandroid.api.Resource
import com.yuanhang.wanandroid.base.BaseViewModel
import com.yuanhang.wanandroid.model.LoginInfo
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * created by yuanhang on 2022/2/11
 * description:
 */
class LoginViewModel @Inject constructor(private val mApi: ApiService): BaseViewModel() {

    fun login(username: String, password: String) = MutableLiveData<Resource<LoginInfo>>().apply {
        value = Resource.loading()
        mUiScope.launch {
            val responseData = mApi.login(username, password)
            value = try {
                Resource.success(responseData.data, responseData.errorMsg)
            } catch (e: Exception) {
                Resource.error(e.message ?: "")
            }
        }
    }
}