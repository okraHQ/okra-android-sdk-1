package com.okra.widget.activities

data class CustomOkraOptions(
    val key: String?,
    val token: String?,
    val products: List<String>?,
    val env: String?,
    val name: String?,
    val short_url: String?
){
    constructor(key: String?,token: String?,products: List<String>?,env: String?,name: String?):this(key, token, products, env, name, null)
    constructor(short_url: String?):this(null, null, null, null, null, short_url)
}