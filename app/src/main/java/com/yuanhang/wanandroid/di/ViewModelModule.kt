package com.yuanhang.wanandroid.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuanhang.wanandroid.base.BaseViewModel
import com.yuanhang.wanandroid.ui.homepage.HomePageViewModel
import com.yuanhang.wanandroid.ui.login.LoginViewModel
import com.yuanhang.wanandroid.ui.main.MainViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * created by yuanhang on 2022/2/10
 * description:
 */
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: WanAndroidViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomePageViewModel::class)
    abstract fun bindHomePageViewModel(homePageViewModel: HomePageViewModel): ViewModel
}