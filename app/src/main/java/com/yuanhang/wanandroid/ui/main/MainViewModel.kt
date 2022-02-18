package com.yuanhang.wanandroid.ui.main

import androidx.lifecycle.MutableLiveData
import com.yuanhang.wanandroid.api.ApiService
import com.yuanhang.wanandroid.api.Resource
import com.yuanhang.wanandroid.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * created by yuanhang on 2022/2/17
 * description:
 */
class MainViewModel @Inject constructor(private val mApi: ApiService): BaseViewModel() {

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