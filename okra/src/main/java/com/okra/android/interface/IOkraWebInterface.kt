package com.okra.android.`interface`

internal interface IOkraWebInterface {

    fun onSuccess(json : String)

    fun onError(json : String)

    fun onClose(json : String)

    fun exitModal(json : String)
}