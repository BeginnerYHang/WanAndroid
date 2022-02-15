package com.yuanhang.wanandroid.di

import android.app.Application
import com.yuanhang.wanandroid.WanAndroidApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * created by yuanhang on 2022/2/10
 * description:
 */
@Singleton
@Component( modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ActivityModule::class,
    FragmentModule::class,
    ViewModelModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: WanAndroidApplication)
}