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
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.gson.Gson
import com.okra.android.R
import com.okra.android.R.id.ok_webview
import com.okra.android.R.id.progressBar
import com.okra.android.`interface`.IOkraWebInterface
import com.okra.android.models.OkraOptions
import com.okra.android.utils.OkraWebInterface

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

    private lateinit var intentForResult :Intent
    private lateinit var webView: WebView
    private lateinit var telephonyManager: TelephonyManager
    private lateinit var okraProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(supportActionBar !=null)
            this.supportActionBar?.hide();
        setContentView(R.layout.activity_okra_main)

        //This gets in the serialized string to send to the server
        val okraStringObject = intent.getStringExtra(OKRA_OBJECT) ?: throw IllegalStateException("Field $OKRA_OBJECT missing in Intent")
        val okraModel = Gson().fromJson(okraStringObject, OkraOptions::class.java)
        okraModel.also{
            it.deviceInfo = getDeviceId()
            it.source = "android"
        }

        val okraObject = Gson().toJson(okraModel)

        intentForResult = Intent()
        webView = findViewById(ok_webview)
        setupWebView()
        okraProgressBar = findViewById(progressBar)
        setupWebClient(okraObject)
    }

    //Setup webviewclient.
    private fun setupWebClient(okraObject: String) {
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                okraProgressBar.visibility = View.GONE

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript("openOkraWidget('${Gson().toJson(Gson().fromJson(okraObject,Any::class.java))}');", null)
                } else {
                    webView.loadUrl("openOkraWidget('${Gson().toJson(Gson().fromJson(okraObject,Any::class.java))}');")
                }
            }
        }
    }

    //Sets up webview
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.addJavascriptInterface(OkraWebInterface(this), "Android")
        webView.loadUrl("https://mobile.okra.ng/")
    }

    //This event gets called when a user carries out a successful operation.
    override fun onSuccess(json: String) {
        intentForResult.putExtra(OKRA_RESULT, json)
        setResult(Activity.RESULT_OK,intentForResult)
        finish()
    }

    //This event gets called when a user encounters an error.
    override fun onError(json: String) {
        intentForResult.putExtra(OKRA_RESULT, json)
        setResult(Activity.RESULT_CANCELED,intentForResult)
        finish()
    }

    //This event gets called when a user closes the okra modal
    override fun onClose(json: String) {
        intentForResult.putExtra(OKRA_RESULT, json)
        setResult(Activity.RESULT_CANCELED,intentForResult)
        finish()
    }

    //This event gets called when onEvent function is triggered
    override fun onEvent(json: String) {
        intentForResult.putExtra(OKRA_RESULT, json)
        setResult(Activity.RESULT_CANCELED,intentForResult)
        finish()
    }

    //This event gets called when a user exit the okra modal
    override fun exitModal(json: String) {
        intentForResult.putExtra(OKRA_RESULT, json)
        setResult(Activity.RESULT_CANCELED,intentForResult)
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
            .setPositiveButton("Yes"){dialogInterface, which ->
                onClose("closed")
            }
            .create().show()
    }

    private fun getDeviceId() : String{
        try {
            telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (ActivityCompat.checkSelfPermission(this@OkraMainActivity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this@OkraMainActivity, arrayOf(Manifest.permission.READ_PHONE_STATE), 10)
            }
            else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Toast.makeText(this,"imei " + telephonyManager.imei, Toast.LENGTH_SHORT).show()
                    return telephonyManager.imei
                } else {
                    Toast.makeText(this, "deviceid " + telephonyManager.deviceId, Toast.LENGTH_SHORT).show()
                    return telephonyManager.deviceId
                }

            }
        }
        catch (e:Exception){

            return ""
        }

        return  ""
    }
}