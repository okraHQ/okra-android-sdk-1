package com.okra.android.models

data class OkraOptions(
    val key: String?,
    val token: String?,
    val products: List<String>?,
    val env: String?,
    val name: String?,
    val short_url: String?,
    var source: String = "android",
    var deviceInfo: String?

){
    constructor(key: String?,token: String?,products: List<String>?,env: String?,name: String?):this(key, token, products, env, name, null, "android", null)
    constructor(short_url: String?):this(null, null, null, null, null, short_url, "android", null)
}