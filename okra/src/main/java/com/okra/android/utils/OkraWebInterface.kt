package com.okra.android.utils

import android.webkit.JavascriptInterface
import com.okra.android.`interface`.IOkraWebInterface

internal class OkraWebInterface(private val okraWebInterface: IOkraWebInterface) {
    @JavascriptInterface
    fun onSuccess(json : String) = okraWebInterface.onSuccess(json)

    @JavascriptInterface
    fun onError(json : String) = okraWebInterface.onError(json)

    @JavascriptInterface
    fun onClose(json : String) = okraWebInterface.onClose(json)

    @JavascriptInterface
    fun exitModal(json : String) = okraWebInterface.exitModal(json)
}