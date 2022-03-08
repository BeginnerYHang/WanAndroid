package com.yuanhang.wanandroid.ui.project

import androidx.lifecycle.MutableLiveData
import com.yuanhang.wanandroid.api.ApiService
import com.yuanhang.wanandroid.api.Resource
import com.yuanhang.wanandroid.base.BaseViewModel
import com.yuanhang.wanandroid.model.CommonPage
import com.yuanhang.wanandroid.model.Level
import com.yuanhang.wanandroid.model.Project
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * created by yuanhang on 2022/3/5
 * description:
 */
class ProjectViewModel @Inject constructor(
    val mApi: ApiService
): BaseViewModel() {

    fun getProjectKinds() = MutableLiveData<Resource<List<Level>>>().apply {
        mUiScope.launch {
            value = try {
                val response = mApi.getProjectKinds()
                Resource.success(response.data, response.errorMsg, response.errorCode)
            } catch (e: Exception) {
                Resource.error(e.message?: "")
            }
        }
    }

    fun getProjectList(pageIndex: Int, kindId: Int) = MutableLiveData<Resource<CommonPage<Project>>>().apply {
        mUiScope.launch {
            value = try {
                val response = mApi.getProjects(pageIndex,kindId)
                Resource.success(response.data, response.errorMsg, response.errorCode)
            } catch (e: Exception) {
                Resource.error(e.message?: "")
            }
        }
    }
}