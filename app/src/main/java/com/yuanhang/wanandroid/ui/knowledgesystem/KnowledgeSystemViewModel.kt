package com.yuanhang.wanandroid.ui.knowledgesystem

import androidx.lifecycle.MutableLiveData
import com.yuanhang.wanandroid.api.ApiService
import com.yuanhang.wanandroid.api.Resource
import com.yuanhang.wanandroid.base.BaseViewModel
import com.yuanhang.wanandroid.model.Level
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * created by yuanhang on 2022/3/2
 * description:
 */
class KnowledgeSystemViewModel @Inject constructor(private val mApi: ApiService): BaseViewModel() {

    fun getKnowledgeSystemData() = MutableLiveData<Resource<List<Level>>>().apply {
        mUiScope.launch {
            value = try {
                val response = mApi.getKnowledgeSystem()
                Resource.success(response.data, response.errorMsg, response.errorCode)
            }catch (e: Exception) {
                Resource.error(e.message?: "")
            }
        }
    }
}