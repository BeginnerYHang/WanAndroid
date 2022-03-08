package com.yuanhang.wanandroid.ui.my

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuanhang.wanandroid.api.ApiService
import com.yuanhang.wanandroid.api.Resource
import com.yuanhang.wanandroid.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * created by yuanhang on 2022/2/21
 * description:
 */
class UserInfoViewModel @Inject constructor(val mApi: ApiService): BaseViewModel() {

    var userId: Int? = null
    var isGuest: Boolean = false

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