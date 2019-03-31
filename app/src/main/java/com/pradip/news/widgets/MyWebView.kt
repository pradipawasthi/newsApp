package com.pradip.news.widgets

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.webkit.*
import java.io.IOException
import java.util.*


/**
 * Created by pradip on 2/17/17.
 */

class MyWebView : WebView {

    internal var mLoadReporter: ContentLoadReporter? = null

    interface ContentLoadReporter {

        fun onWebPageLoadStart(url: String)

        fun onWebPageLoadComplete(url: String)

        fun onWebPageLoadProgressUpdate(url: String, progress: Int)

        fun onWebPageLoadError(url: String, contextMessage: String)
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        //
        if (Build.VERSION.SDK_INT >= SDK_VERSION_LOLLIPOP) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
        } else {
            setLayerType(View.LAYER_TYPE_NONE, null)
        }

        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                if (mLoadReporter != null) {
                    mLoadReporter!!.onWebPageLoadProgressUpdate(url, newProgress)
                }
            }

        }

        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false

        settings.javaScriptEnabled = true
        settings.setSupportZoom(false)
        settings.builtInZoomControls = false
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        settings.pluginState = WebSettings.PluginState.ON_DEMAND
        // if you want transparent
        setBackgroundColor(0x00000000)
    }

    /**
     * Due to bug in 4.2.2 sometimes the webView will not draw its contents
     * after the data has loaded. Triggers redraw. Does not work in webView's
     * onPageFinished callback
     */
    private fun forceWebViewRedraw() {
        post {
            if (context is Activity) {
                val activity = context as Activity

                invalidate()
            }
        }
    }

    fun setContentLoadReporter(loadReporter: ContentLoadReporter) {
        mLoadReporter = loadReporter
    }

    /**
     * On content load, clear view first then force request layout, ensures proper height/width
     */
    fun loadWithUrl(url: String) {
        if (TextUtils.isEmpty(url)) {
            return
        }
        // If you run into layout issues you may want to call clearView().
        // Not calling this cause it may cause a UI flicker
        loadUrl(url)
        requestLayout()
    }

    /**
     * On content load, clear view first then force request layout, ensures proper height/width
     */
    fun loadWithHtmlContent(content: String) {
        if (TextUtils.isEmpty(content)) {
            return
        }
        // If you run into layout issues you may want to call clearView().
        // Not calling this cause it may cause a UI flicker
        loadDataWithBaseURL("", content, "text/html", "utf-8", null)
        requestLayout()
    }

    override fun onDetachedFromWindow() {
        // TODO: remove any javascript interface hooks.
        // removeJavascriptInterface(JAVASCRIPT_INTERFACE_NAME);
        mLoadReporter = null

        loadUrl("about:blank") // loads non-dynamic content

        clearCache(true)
        destroyDrawingCache()
        webChromeClient = null
        webViewClient = null

        removeAllViews()
        destroy()

        super.onDetachedFromWindow()
    }


    @Throws(IOException::class)
    private fun getFontResource(url: String): WebResourceResponse? {
        val filename = url.substring(url.lastIndexOf('/') + 1, url.length)
        val filepath = fontFolder + filename
        return getWebResourceResponseForFilepath(filepath)
    }

    @Throws(IOException::class)
    private fun getWebResourceResponseForFilepath(filepath: String): WebResourceResponse? {
        var resp: WebResourceResponse? = null
        val data = context.assets.open(filepath)
        if (data != null) {
            val mimeType = getMimeTypeFromFilepath(filepath)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val responseHeaders = HashMap<String, String>()
                responseHeaders["Access-Control-Allow-Origin"] = "*"
                resp = WebResourceResponse(mimeType, encoding, 200, "OK", responseHeaders, data)
            } else {
                resp = WebResourceResponse(mimeType, encoding, data)
            }
        } else {
            Log.w(TAG, "data was null for filepath: $filepath")
        }

        return resp
    }

    private fun getMimeTypeFromFilepath(filepath: String): String {
        var mimeType = ""
        val extension = MimeTypeMap.getFileExtensionFromUrl(filepath)
        when (extension) {
            "otf" -> mimeType = "application/x-font-opentype"
            "woff" -> mimeType = "application/font-woff"
            "js" -> mimeType = "text/javascript"
        }
        return mimeType
    }

    companion object {

        private val SDK_VERSION_LOLLIPOP = 21

        private val TAG = MyWebView::class.java.simpleName
        private val fontFolder = "fonts/"
        private val encoding = "UTF-8"
    }

}
