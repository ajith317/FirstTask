package com.example.firsttask

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Dialog
import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*


class DayEigteenTask : AppCompatActivity(){

    lateinit var appleLogin:Button

    val client_id="com.trioangle.gofer.clientid"
    val callback_url=" https://gofer.trioangle.com/api/apple_callback"
    lateinit var appledialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_eigteen_task)

        appleLogin=findViewById(R.id.apple_login_btn)

        val state=UUID.randomUUID().toString()

        appleLogin.setOnClickListener({
            setupAppleWebviewDialog(callback_url)
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setupAppleWebviewDialog(url: String) {
        appledialog = Dialog(this)
        val webView = WebView(this)
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false
        webView.webViewClient = AppleWebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
        appledialog.setContentView(webView)
        appledialog.show()
    }

    @Suppress("OverridingDeprecatedMember")
    inner class AppleWebViewClient : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (request?.url.toString().startsWith(callback_url)) {

                return true
            }
            return true
        }

        // For API 19 and below
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url.startsWith(callback_url)) {



                // Close the dialog after getting the authorization code
                if (url.contains("success=")) {
                    appledialog.dismiss()
                }
                return true
            }
            return false
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            // retrieve display dimensions
            val displayRectangle = Rect()
            val window = this@DayEigteenTask.window
            window.decorView.getWindowVisibleDisplayFrame(displayRectangle)

            // Set height of the Dialog to 90% of the screen
            val layoutParams = view?.layoutParams
            layoutParams?.height = (displayRectangle.height() * 0.9f).toInt()
            view?.layoutParams = layoutParams
        }


    }
}
