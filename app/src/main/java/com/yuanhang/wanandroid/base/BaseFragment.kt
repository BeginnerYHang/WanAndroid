package com.yuanhang.wanandroid.base

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.di.WanAndroidViewModelFactory
import com.yuanhang.wanandroid.ui.common.ImageLoader
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * created by yuanhang on 2022/2/10
 * description:
 */
open class BaseFragment: Fragment(), HasAndroidInjector {

    @Inject
    lateinit var mAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var mViewModelFactory: WanAndroidViewModelFactory

    @Inject
    lateinit var mImageLoader: ImageLoader

    private lateinit var mCustomToast: CustomToast

    override fun androidInjector(): AndroidInjector<Any> {
        return mAndroidInjector
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        mImageLoader.setUpFragment(this)
        mCustomToast = CustomToastImpl(context)
    }

    fun <T: BaseViewModel> getViewModel(owner: ViewModelStoreOwner, clazz: Class<T>): T {
        return ViewModelProvider(owner, mViewModelFactory).get(clazz)
    }

    fun toast(message: String, duration: Int = Toast.LENGTH_SHORT) =
        mCustomToast.show(message, null, duration)

    fun toastSuccess(message: String, duration: Int = Toast.LENGTH_SHORT) =
        mCustomToast.show(message, R.drawable.ic_tips_succeed, duration)

    fun toastInform(message: String, duration: Int = Toast.LENGTH_SHORT) =
        mCustomToast.show(message, R.drawable.ic_tips_inform, duration)

    fun toastFail(message: String, duration: Int = Toast.LENGTH_SHORT) =
        mCustomToast.show(message, R.drawable.ic_tips_fail, duration)
}