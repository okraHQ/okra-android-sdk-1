package com.okra.android.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.KeyEvent
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.okra.android.R
import com.okra.android.R.id.ok_webview
import com.okra.android.`interface`.IOkraWebInterface
import com.okra.android.models.OkraOptions
import com.okra.android.utils.OkraWebInterface
import java.util.*

//Handles all Okra operation
class OkraMainActivity : AppCompatActivity(), IOkraWebInterface {

    companion object {
        const val OKRA_OBJECT = "okraObject"
        const val OKRA_RESULT = "okraResult"

        fun newIntent(context: Context, okraOptions: OkraOptions): Intent {
            val intent = Intent(context, OkraMainActivity::class.java)
            intent.putExtra(OKRA_OBJECT, Gson().toJson(okraOptions))
            return intent
        }
    }

    private lateinit var intentForResult: Intent
    private lateinit var webView: WebView
    private lateinit var telephonyManager: TelephonyManager
    private lateinit var okraProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null)
            this.supportActionBar?.hide()

        setContentView(R.layout.activity_okra_main)

        //This gets in the serialized string to send to the server
        val okraStringObject = intent.getStringExtra(OKRA_OBJECT)
            ?: throw IllegalStateException("Field $OKRA_OBJECT missing in Intent")
        val okraModel = Gson().fromJson(okraStringObject, OkraOptions::class.java)

        if(okraModel.products != null){
            okraModel.products.indices.forEach { i ->
                okraModel.products[i] = "'${okraModel.products[i]}'"
            }
        }


        intentForResult = Intent()
        webView = findViewById(ok_webview)
        setupWebView(okraModel)
    }

    //Sets up webview
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(okraModel: OkraOptions) {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.addJavascriptInterface(OkraWebInterface(this), "Android")

        val htmlToSend = getHtmlToSend(okraModel)
        webView.loadDataWithBaseURL(null, htmlToSend, "text/html", "utf-8", null)
    }

    private fun getHtmlToSend(okraModel: OkraOptions): String {
        if (okraModel.short_url != null && okraModel.short_url != "")
            return getShortUrlHtml(okraModel.short_url)
        else
            return getOptionsHtml(okraModel)

    }

    private fun getOptionsHtml(okraObject: OkraOptions): String {

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Okra Android SDK</title>\n" +
                "  </head>\n" +
                "    <body onload=\"buildWithOptions()\" style=\"background-color:#fff;height:100vh\">\n" +
                "      <script src=\"https://cdn.okra.ng/v2/bundle.js\"></script>\n" +
                "      <script type=\"text/javascript\">\n" +
                "          window.onload = buildWithOptions; \n" +
                "          function buildWithOptions(){ \n" +
                "              Okra.buildWithOptions({\n" +
                "                name: '${okraObject.name}',\n" +
                "                env: '${okraObject.env}',\n" +
                "                app_id: '${okraObject.app_id}',\n" +
                "                key: '${okraObject.key}', \n" +
                "                token: '${okraObject.token}',  \n" +
                "                payment:${okraObject.payment},\n" +
                "                isCorporate:${okraObject.isCorporate},\n" +
                "                logo:'${okraObject.logo}',\n" +
                "                callback_url:'${okraObject.callback_url}',\n" +
                "                exp:'${okraObject.exp}',\n" +
                "                connectMessage:'${okraObject.connectMessage}',\n" +
                "                widget_success:'${okraObject.widget_success}',\n" +
                "                widget_failed:'${okraObject.widget_failed}',\n" +
                "                currency:'${okraObject.currency}',\n" +
                "                filter: ${okraObject.filter},\n" +
                "                source: 'android',\n" +
                "                color: '${okraObject.color}',\n" +
                "                deviceInfo: '${getDeviceId()}',\n" +
                "                limit: ${okraObject.limit},\n" +
                "                products: ${okraObject.products},\n" +
                "                charge:${Gson().toJson(okraObject.charge)},\n" +
                "                onSuccess: function(data){\n" +
                "                      let response = {event:'option success', data}\n" +
                "                      Android.onSuccess(JSON.stringify(data))\n" +
                "                  },\n" +
                "                onClose: function(){\n" +
                "                      let response = {event:'option close'}\n" +
                "                      Android.onClose(JSON.stringify(response))\n" +
                "                  },\n" +
                "                  BeforeClose: function(){\n" +
                "                    let response = {event:'option before close'}\n" +
                "                    Android.BeforeClose(JSON.stringify(response))\n" +
                "                },\n" +
                "                onError: function(data){\n" +
                "                  let response = {event:'option error', data}\n" +
                "                  Android.onError(JSON.stringify(response))\n" +
                "              }\n" +
                "              })\n" +
                "          }\n" +
                "      </script> \n" +
                "    </body>\n" +
                "</html> "
    }

    private fun getShortUrlHtml(s: String): String {
        return "<!DOCTYPE html>\n" +
                "      <html lang=\"en\">\n" +
                "        <head>\n" +
                "          <meta charset=\"UTF-8\">\n" +
                "          <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "          <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "          <title>Okra Android SDK</title>\n" +
                "        </head>\n" +
                "          <body  onload=\"buildWithShortUrl()\" style=\"background-color:#fff;height:100vh\">\n" +
                "            <script src=\"https://cdn.okra.ng/v2/bundle.js\"></script>\n" +
                "            <script type=\"text/javascript\">\n" +
                "                window.onload = buildWithShortUrl;\n" +
                "                function buildWithShortUrl(){ \n" +
                "                    Okra.buildWithShortUrl({\n" +
                "                        short_url: '${s}',\n" +
                "                        onSuccess: function(data){\n" +
                "                            let response = {event:'option success', data}\n" +
                "                            Android.onSuccess(JSON.stringify(data))\n" +
                "                        },\n" +
                "                        onClose: function(){\n" +
                "                            let response = {event:'option close'}\n" +
                "                            Android.onClose(JSON.stringify(response))\n" +
                "                        },\n" +
                "                        BeforeClose: function(){\n" +
                "                          let response = {event:'option before close'}\n" +
                "                          Android.BeforeClose(JSON.stringify(response))\n" +
                "                      },\n" +
                "                      onError: function(data){\n" +
                "                        let response = {event:'option error', data}\n" +
                "                        Android.onError(JSON.stringify(data))\n" +
                "                    }\n" +
                "                    })\n" +
                "                }\n" +
                "            </script> \n" +
                "          </body>\n" +
                "      </html> "
    }

    //This event gets called when a user carries out a successful operation.
    override fun onSuccess(json: String) {
        intentForResult.putExtra(OKRA_RESULT, json)
        setResult(Activity.RESULT_OK, intentForResult)
        finish()
    }

    //This event gets called when a user encounters an error.
    override fun onError(json: String) {
        intentForResult.putExtra(OKRA_RESULT, json)
        setResult(Activity.RESULT_CANCELED, intentForResult)
        finish()
    }

    //This event gets called when a user closes the okra modal
    override fun onClose(json: String) {
        intentForResult.putExtra(OKRA_RESULT, json)
        setResult(Activity.RESULT_CANCELED, intentForResult)
        finish()
    }

    //This event gets called when onEvent function is triggered
    override fun onEvent(json: String) {
        intentForResult.putExtra(OKRA_RESULT, json)
        setResult(Activity.RESULT_CANCELED, intentForResult)
        finish()
    }

    //This event gets called when a user exit the okra modal
    override fun exitModal(json: String) {
        intentForResult.putExtra(OKRA_RESULT, json)
        setResult(Activity.RESULT_CANCELED, intentForResult)
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, exit the activity)
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Exit")
            .setMessage("Are you sure?")
            .setNegativeButton("No", null)
            .setPositiveButton("Yes") { dialogInterface, which ->
                onClose("closed")
            }
            .create().show()
    }

    private fun getDeviceId(): String {
        try {
            telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (ActivityCompat.checkSelfPermission(
                    this@OkraMainActivity,
                    Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@OkraMainActivity,
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    10
                )
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return telephonyManager.imei
                } else {
                    return telephonyManager.deviceId
                }

            }
        } catch (e: Exception) {

            return ""
        }

        return ""
    }
}