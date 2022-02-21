package com.yuanhang.wanandroid.ui.common

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.JsPromptResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.yuanhang.wanandroid.R
import com.yuanhang.wanandroid.util.gone
import com.yuanhang.wanandroid.util.onClick
import com.yuanhang.wanandroid.util.visible
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    lateinit var homeUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        WebViewInitializer(
            this,
            webView,
            MyWebChromeClient(),
            WebViewClient()
        ).setDefaults()

        intent.getStringExtra(ARG_TITLE)?.let {
            tvTitle.text = it
        }
        homeUrl = intent.getStringExtra(ARG_HOME_URL) ?: ""
        webView.loadUrl(homeUrl)
        ivBack.onClick {
            onBackPressed()
        }
        ivClose.onClick {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.loadUrl("about:blank")
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    inner class MyWebChromeClient : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            if (tvTitle.text.toString().isEmpty()) {
                tvTitle.text = title
            }
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress == 100 && pbWebView.isVisible) {
                pbWebView.gone()
                webView.visible()
            }
            ivClose.isVisible = webView.canGoBack()
        }

        override fun onJsPrompt(
            view: WebView?,
            url: String?,
            message: String?,
            defaultValue: String?,
            result: JsPromptResult?
        ): Boolean {
            return onJsPrompt(message, result)
        }
    }

    fun runJs(javascript: String) {
        webView.loadUrl("javascript:$javascript")
    }

    fun onJsPrompt(message: String?, result: JsPromptResult?): Boolean {
        return false
    }

    companion object {
        const val ARG_HOME_URL = "home_url"
        const val ARG_TITLE = "title"

        fun start(from: Activity, homeUrl: String? = null, title: String? = null) {
            val intent = Intent(from, WebViewActivity::class.java).apply {
                putExtra(ARG_HOME_URL, homeUrl)
                putExtra(ARG_TITLE, title)
            }
            from.startActivity(intent)
        }
    }
}