package com.yuanhang.wanandroid.di

import com.yuanhang.wanandroid.ui.main.MainActivity
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * created by yuanhang on 2022/2/10
 * description:
 */
@Module
internal abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun baseActivityInjector(): BaseActivity

    @ContributesAndroidInjector
    internal abstract fun mainActivityInjector(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun loginActivityInjector(): LoginActivity
}