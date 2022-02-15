package com.yuanhang.wanandroid.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * created by yuanhang on 2022/2/11
 * description:
 */
open class BaseViewModel: ViewModel() {
    private val mViewModelJob = Job()
    protected val mUiScope = CoroutineScope(Dispatchers.Main + mViewModelJob)

    override fun onCleared() {
        super.onCleared()
        mViewModelJob.cancel()
    }
}