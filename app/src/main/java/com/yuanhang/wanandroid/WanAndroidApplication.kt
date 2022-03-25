package com.yuanhang.wanandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.yuanhang.wanandroid.base.BaseActivity
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

    private val mActivities = mutableListOf<Activity>()

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build().inject(this)
        app = this
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                mActivities.add(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                mActivities.remove(activity)
            }
        })
    }

    fun getCurrentOpenedActivities() = mActivities

    fun getCurrentTopActivity() = if (mActivities.isEmpty()) null else mActivities[mActivities.size - 1]

    companion object {
        lateinit var app: WanAndroidApplication
    }
}