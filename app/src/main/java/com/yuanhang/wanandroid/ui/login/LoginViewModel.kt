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
            value = try {
                Resource.success(mApi.login(username, password).data)
            } catch (e: Exception) {
                Resource.error(e.message ?: "")
            }
        }
    }
}