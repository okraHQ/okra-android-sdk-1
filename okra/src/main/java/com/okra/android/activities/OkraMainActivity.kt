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
import com.okra.android.utils.OkraWebInterface
import java.util.*

//Handles all Okra operation
class OkraMainActivity : AppCompatActivity(), IOkraWebInterface {

    companion object {
        const val OKRA_OBJECT = "okraObject"
        const val OKRA_RESULT = "okraResult"
        private const val SOURCE = "source"
        private const val LOGO = "logo"
        private const val logoUrl = ""
        private const val COLOR = "color"
        private const val PAYMENT = "payment"
        private const val FILTER = "filter"
        private const val KEY = "key"
        private const val ENV = "env"
        private const val isCorporate = "isCorporate"
        private const val TOKEN = "token"
        private const val APPID = "app_id"
        private const val LIMIT = "limit"
        private const val Currency = "currency"
        private const val WidgetSuccess = "widget_success"
        private const val WidgetFailed = "widget_failed"
        private const val CallbackUrl = "callback_url"
        private const val ConnectMessage = "connectMessage"
        private const val EXP = "exp"
        private const val CHARGE = "charge"
        private const val NAME = "name"
        private const val PRODUCTS = "products"
        private const val DEVICE_INFO = "deviceInfo"

        fun newIntent(context: Context, okraOptions: Any): Intent {
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
            this.supportActionBar?.hide();

        setContentView(R.layout.activity_okra_main)

        //This gets in the serialized string to send to the server
        val okraStringObject = intent.getStringExtra(OKRA_OBJECT)
            ?: throw IllegalStateException("Field $OKRA_OBJECT missing in Intent")
        val okraModel = Gson().fromJson(okraStringObject, Any::class.java)

        val convertObject = addCustomProperty(okraModel)

        val okraObject = Gson().toJson(convertObject)

        intentForResult = Intent()
        webView = findViewById(ok_webview)
        setupWebView(okraModel)
        //setupWebClient(okraObject)
    }

    private fun addCustomProperty(okraModel: Any?): Any {

        val editOkraObject = okraModel as LinkedTreeMap<String, Any>

        if(editOkraObject.containsKey(PRODUCTS)){
            val products = editOkraObject[PRODUCTS] as ArrayList<String>
            for (i in products.indices)
            {
                products[i] = "'${products[i]}'"
            }
            editOkraObject[PRODUCTS] = products
        }

        if (editOkraObject.contains(SOURCE) && editOkraObject[SOURCE] == null && editOkraObject[SOURCE] == "" && editOkraObject[SOURCE] != "android") {
            editOkraObject[SOURCE] = "android"
        }

        if (!editOkraObject.contains(SOURCE)) {
            editOkraObject[SOURCE] = "android"
        }

        if (editOkraObject.contains(DEVICE_INFO) && editOkraObject[DEVICE_INFO] == null && editOkraObject[DEVICE_INFO] == "") {
            editOkraObject[DEVICE_INFO] = getDeviceId()
        }

        if (!editOkraObject.contains(DEVICE_INFO)) {
            editOkraObject[DEVICE_INFO] = getDeviceId()
        }

        if (editOkraObject.contains(LOGO) && editOkraObject[LOGO] == null && editOkraObject[LOGO] == "") {
            editOkraObject[LOGO] = logoUrl
        }

        if (!editOkraObject.contains(LOGO)) {
            editOkraObject[LOGO] = logoUrl
        }

        if (editOkraObject.contains(COLOR) && editOkraObject[COLOR] == null && editOkraObject[COLOR] == "") {
            editOkraObject[COLOR] = "#3AB795"
        }

        if (!editOkraObject.contains(COLOR)) {
            editOkraObject[COLOR] = "#3AB795"
        }

        if (editOkraObject.contains(PAYMENT) && editOkraObject[PAYMENT] == null && editOkraObject[PAYMENT] == "") {
            editOkraObject[PAYMENT] = false
        }

        if (!editOkraObject.contains(PAYMENT)) {
            editOkraObject[PAYMENT] = false
        }
        if (editOkraObject.contains(FILTER) && editOkraObject[FILTER] == null && editOkraObject[FILTER] == "") {
            editOkraObject[FILTER] = listOf<String>()
        }

        if (!editOkraObject.contains(FILTER)) {
            editOkraObject[FILTER] = listOf<String>()
        }

        if (editOkraObject.contains(Currency) && editOkraObject[Currency] == null && editOkraObject[Currency] == "") {
            editOkraObject[Currency] = "NGN"
        }

        if (!editOkraObject.contains(Currency)) {
            editOkraObject[Currency] = "NGN";
        }

        if (editOkraObject.contains(EXP) && editOkraObject[EXP] == null && editOkraObject[EXP] == "") {
            editOkraObject[EXP] = Date()
        }

        if (!editOkraObject.contains(EXP)) {
            editOkraObject[EXP] = Date()
        }

        if (editOkraObject.contains(ConnectMessage) && editOkraObject[ConnectMessage] == null && editOkraObject[ConnectMessage] == "") {
            editOkraObject[ConnectMessage] = ""
        }

        if (!editOkraObject.contains(ConnectMessage)) {
            editOkraObject[ConnectMessage] = ""
        }

        if (editOkraObject.contains(LIMIT) && editOkraObject[LIMIT] == null && editOkraObject[LIMIT] == "") {
            editOkraObject[LIMIT] = 24
        }

        if (!editOkraObject.contains(LIMIT)) {
            editOkraObject[LIMIT] = 24
        }

        if (editOkraObject.contains(WidgetSuccess) && editOkraObject[WidgetSuccess] == null && editOkraObject[WidgetSuccess] == "") {
            editOkraObject[WidgetSuccess] = ""
        }

        if (!editOkraObject.contains(WidgetSuccess)) {
            editOkraObject[WidgetSuccess] = ""
        }

        if (editOkraObject.contains(WidgetFailed) && editOkraObject[WidgetFailed] == null && editOkraObject[WidgetFailed] == "") {
            editOkraObject[WidgetFailed] = ""
        }

        if (!editOkraObject.contains(WidgetFailed)) {
            editOkraObject[WidgetFailed] = ""
        }

        if (editOkraObject.contains(CHARGE) && editOkraObject[CHARGE] == null && editOkraObject[CHARGE] == "") {
            editOkraObject[CHARGE] = listOf<String>()
        }

        if (!editOkraObject.contains(CHARGE)) {
            editOkraObject[CHARGE] = listOf<String>()
        }

        if (editOkraObject.contains(APPID) && editOkraObject[APPID] == null && editOkraObject[APPID] == "") {
            editOkraObject[APPID] = null
        }

        if (!editOkraObject.contains(APPID)) {
            editOkraObject[APPID] = null
        }

        if (editOkraObject.contains(isCorporate) && editOkraObject[isCorporate] == null && editOkraObject[isCorporate] == "") {
            editOkraObject[isCorporate] = false
        }

        if (!editOkraObject.contains(isCorporate)) {
            editOkraObject[isCorporate] = false
        }

        return editOkraObject
    }

    //Sets up webview
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(okraModel: Any) {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.addJavascriptInterface(OkraWebInterface(this), "Android")

        val htmlToSend = getHtmlToSend(okraModel)
        webView.loadDataWithBaseURL(null, htmlToSend, "text/html", "utf-8", null)
    }

    private fun getHtmlToSend(okraModel: Any): String {

        val okraObject = okraModel as LinkedTreeMap<String, Any>

        if (okraObject.containsKey("short_url") && okraObject["short_url"] != null && okraObject["short_url"] != "")
            return getShortUrlHtml(okraObject["short_url"] as String)
        else
            return getOptionsHtml(okraObject)

    }

    private fun getOptionsHtml(okraObject: LinkedTreeMap<String, Any>): String {

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
                "                name: '${okraObject[NAME]}',\n" +
                "                env: '${okraObject[ENV]}',\n" +
                "                app_id: '${okraObject[APPID]}',\n" +
                "                key: '${okraObject[KEY]}', \n" +
                "                token: '${okraObject[TOKEN]}',  \n" +
                "                payment:${okraObject[PAYMENT]},\n" +
                "                isCorporate:${okraObject[isCorporate]},\n" +
                "                logo:'${okraObject[LOGO]}',\n" +
                "                callback_url:'${okraObject[CallbackUrl]}',\n" +
                "                exp:'${okraObject[EXP]}',\n" +
                "                connectMessage:'${okraObject[ConnectMessage]}',\n" +
                "                widget_success:'${okraObject[WidgetSuccess]}',\n" +
                "                widget_failed:'${okraObject[WidgetFailed]}',\n" +
                "                currency:'${okraObject[Currency]}',\n" +
                "                filter: ${okraObject[FILTER]},\n" +
                "                source: 'android',\n" +
                "                color: '${okraObject[COLOR]}',\n" +
                "                deviceInfo: '${okraObject[DEVICE_INFO]}',\n" +
                "                limit: '${okraObject[LIMIT]}',\n" +
                "                products: ${okraObject[PRODUCTS]},\n" +
                "                charge:${okraObject[CHARGE]},\n" +
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
                "                            Android.onSuccess(data)\n" +
                "                        },\n" +
                "                        onClose: function(){\n" +
                "                            let response = {event:'option close'}\n" +
                "                            Android.onClose(response)\n" +
                "                        },\n" +
                "                        BeforeClose: function(){\n" +
                "                          let response = {event:'option before close'}\n" +
                "                          Android.BeforeClose(response)\n" +
                "                      },\n" +
                "                      onError: function(data){\n" +
                "                        let response = {event:'option error', data}\n" +
                "                        Android.onError(data)\n" +
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