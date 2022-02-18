package com.yuanhang.wanandroid.ui.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.RoundedCorner
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.yuanhang.wanandroid.base.BaseActivity
import com.yuanhang.wanandroid.base.BaseFragment
import jp.wasabeef.glide.transformations.BlurTransformation
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * created by yuanhang on 2022/2/18
 * description:
 */
class ImageLoader @Inject constructor() {
    private var mActivity = WeakReference<BaseActivity>(null)
    private var mFragment = WeakReference<BaseFragment>(null)
    private var mContext = WeakReference<Context>(null)

    fun setUpActivity(activity: BaseActivity) {
        mActivity = WeakReference(activity)
    }

    fun setUpFragment(fragment: BaseFragment) {
        mFragment = WeakReference(fragment)
    }

    fun setUpContext(context: Context) {
        mContext = WeakReference(context)
    }

    fun load(
        url: String,
        imageView: ImageView,
        @DrawableRes resourceHolderId: Int = 0,
        cornerRadius: Int = 0,
        needBlur: Boolean = false,
        callback: ((Drawable?) -> Unit)? = null
    ) {
        mActivity.get()?.let {
            if (it.isDestroyed) {
                return
            }
            val requestBuilder = Glide.with(it).load(url)
            if (resourceHolderId != 0){
                requestBuilder.placeholder(resourceHolderId)
            }
            if (cornerRadius > 0) {
                requestBuilder.transform(RoundedCorners(cornerRadius), )
            }
            if (needBlur) {
                requestBuilder.transform(BlurTransformation())
            }
            requestBuilder.addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback?.invoke(null)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback?.invoke(resource)
                    return false
                }
            })
            requestBuilder.into(imageView)
            return
        }
        mFragment.get()?.let {
            if (it.activity == null || it.requireActivity().isDestroyed) {
                return
            }
            val requestBuilder = Glide.with(it).load(url)
            if (resourceHolderId != 0){
                requestBuilder.placeholder(resourceHolderId)
            }
            if (cornerRadius > 0) {
                requestBuilder.transform(RoundedCorners(cornerRadius), )
            }
            if (needBlur) {
                requestBuilder.transform(BlurTransformation())
            }
            requestBuilder.addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback?.invoke(null)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback?.invoke(resource)
                    return false
                }
            })
            requestBuilder.into(imageView)
            return
        }
        mContext.get()?.let {
            val requestBuilder = Glide.with(it).load(url)
            if (resourceHolderId != 0){
                requestBuilder.placeholder(resourceHolderId)
            }
            if (cornerRadius > 0) {
                requestBuilder.transform(RoundedCorners(cornerRadius), )
            }
            if (needBlur) {
                requestBuilder.transform(BlurTransformation())
            }
            requestBuilder.addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback?.invoke(null)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback?.invoke(resource)
                    return false
                }
            })
            requestBuilder.into(imageView)
        }
    }
}