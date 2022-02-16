package com.yuanhang.wanandroid

import android.app.Application
import android.content.Context
import com.yuanhang.wanandroid.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * created by yuanhang on 2022/2/10
 * description:
 */
class WanAndroidApplication: Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build().inject(this)
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
    }
}