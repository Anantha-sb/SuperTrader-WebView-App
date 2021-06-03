package com.example.supertradermarkets

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient


// This activity gets called when the app is launched

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Change URL here of your desired website to build your apk for Android
        val mWebView : WebView = findViewById<View>(R.id.superWeb) as WebView
        mWebView.loadUrl("https://new.supertraderfx.com/#")

        // JavaScript Settings switch here
        val webSettings : WebSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

        // For security purpose, always have this false
        webSettings.allowFileAccess = false

        // Enables Pinch Zoom inside webview
        mWebView.isHorizontalScrollBarEnabled = false
        mWebView.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
        mWebView.setBackgroundColor(0x80)
        mWebView.settings.setSupportZoom(true)
        mWebView.settings.builtInZoomControls = true
        mWebView.settings.displayZoomControls = false

        mWebView.settings.setAppCacheMaxSize((5 * 1024 * 1024).toLong())
        mWebView.settings.setAppCachePath(applicationContext.cacheDir.absolutePath)
        mWebView.settings.setAppCacheEnabled(true)
        mWebView.settings.cacheMode = WebSettings.LOAD_DEFAULT // load online by default

        if (!isNetworkAvailable) {
            mWebView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }

        mWebView.webViewClient = WebViewClient()
        mWebView.canGoBack()
        mWebView.setOnKeyListener { v, keyCode, event ->

            if (keyCode  == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && mWebView.canGoBack()) {
                mWebView.goBack()
                return@setOnKeyListener true
            }
            false
        }
    }

    // Custom class to handle offline mode
    private val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
}
