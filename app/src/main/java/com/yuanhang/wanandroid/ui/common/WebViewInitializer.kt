package com.yuanhang.wanandroid.ui.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import android.webkit.*
import androidx.annotation.RequiresApi

/**
 * created by yuanhang on 2022/2/18
 * description:
 */
class WebViewInitializer(
    val context: Context,
    val webView: WebView,
    private val webChromeClient: WebChromeClient? = null,
    private val webViewClient: WebViewClient? = null
) {
    init {
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webView.settings.apply {
            defaultTextEncodingName = "UTF-8"
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            domStorageEnabled = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            cacheMode = WebSettings.LOAD_NO_CACHE
            useWideViewPort = true
            loadWithOverviewMode = true
        }
    }

    fun enableCache(): WebViewInitializer {
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        context.cacheDir?.absolutePath?.let {
            webView.settings.setAppCachePath(it)
        }
        webView.settings.setAppCacheEnabled(true)

        return this
    }

    fun enableJavaScript(): WebViewInitializer {
        webView.settings.javaScriptEnabled = true
        webView.removeJavascriptInterface("searchBoxJavaBridge_")

        return this
    }

    fun enableLocalStorage(): WebViewInitializer {
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true

        return this
    }

    fun enableFileAccess(): WebViewInitializer {
        webView.settings.allowFileAccess = true

        return this
    }

    fun enableMixContent(): WebViewInitializer {
        webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        return this
    }

    fun setDefaults(): WebViewInitializer {
        enableCache()
        enableJavaScript()
        enableFileAccess()
        enableLocalStorage()
        enableMixContent()
        webView.webChromeClient = webChromeClient ?: WebChromeClient()
        webView.webViewClient = webViewClient ?: WebViewClient()

        return this
    }
}