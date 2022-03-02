package com.yuanhang.wanandroid.ui.search

import androidx.lifecycle.MutableLiveData
import com.yuanhang.wanandroid.api.ApiService
import com.yuanhang.wanandroid.api.Resource
import com.yuanhang.wanandroid.base.BaseViewModel
import com.yuanhang.wanandroid.model.HottestWord
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * created by yuanhang on 2022/2/22
 * description:
 */
class SearchViewModel @Inject constructor(private val mApi: ApiService): BaseViewModel() {

    fun getHottestWord() = MutableLiveData<Resource<List<HottestWord>>>().apply {
        value = Resource.loading()
        mUiScope.launch {
            value = try {
                Resource.success(mApi.getSearchHottestWord().data)
            }catch (e: Exception) {
                Resource.error(e.message?: "")
            }
        }
    }

//    fun getSearchResult() =  MutableLiveData<Resource<List<>>>
}