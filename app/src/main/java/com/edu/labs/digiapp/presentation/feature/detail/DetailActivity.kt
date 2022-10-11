package com.edu.labs.digiapp.presentation.feature.detail

import android.os.Bundle
import android.webkit.*
import com.edu.labs.digiapp.databinding.ActivityDetailBinding
import com.edu.labs.digiapp.presentation.common.BaseNetworkActivity
import com.edu.labs.digiapp.util.extensions.gone

class DetailActivity : BaseNetworkActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupBack()
        setupWebView()
    }

    private fun setupBinding() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        offlineView = binding.offlineCard
    }

    private fun setupBack() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupWebView() {
        val url = intent?.extras?.getString("url") ?: ""

        with(binding.articleWebView) {
            settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                domStorageEnabled = true
                cacheMode = if (networkManager.checkForInternet()) WebSettings.LOAD_DEFAULT
                else WebSettings.LOAD_CACHE_ELSE_NETWORK
            }
            webChromeClient = WebChromeClient()
            webViewClient = Client()
            loadUrl(url)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    inner class Client : WebViewClient() {

        override fun onReceivedError(
            view: WebView, request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
            binding.loading.gone()
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding.loading.gone()
        }

    }

}