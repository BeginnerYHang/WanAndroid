package com.yuanhang.wanandroid.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.yuanhang.wanandroid.di.WanAndroidViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * created by yuanhang on 2022/2/10
 * description:
 */
open class BaseActivity: AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var mAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var mViewModelFactory: WanAndroidViewModelFactory

    override fun androidInjector(): AndroidInjector<Any> {
        return mAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}