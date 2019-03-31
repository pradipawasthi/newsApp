package com.pradip.news.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.pradip.news.Constants
import com.pradip.news.R
import kotlinx.android.synthetic.main.activity_web_view.*

/**
 * @author pradip; dated 01/04/19.
 *
 * Activity to load url.
 */

class WebViewActivity : AppCompatActivity() {

    private var client: WebViewClient? = null
    private var url: String = ""
    private var title: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        setValues()
        loadWebView()


    }

    private fun setValues() {
        url = intent.getStringExtra(Constants.NEWS_LINK) ?: ""
        title = intent.getStringExtra(Constants.NEWS_TITLE) ?: ""
    }

    private fun loadWebView() {

        webViewNews.loadWithUrl(url)

        client = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return false
            }


            override fun onPageFinished(view: WebView, url: String) {
                progress_url_load!!.visibility = View.GONE
                webViewNews!!.visibility = View.VISIBLE
                layout_overlay!!.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }

        this.webViewNews!!.webViewClient = client
        webViewNews!!.setOnKeyListener(View.OnKeyListener { view, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.action == MotionEvent.ACTION_UP
                    && webViewNews.canGoBack()) {
                return@OnKeyListener true
            }

            false
        })
    }


}
