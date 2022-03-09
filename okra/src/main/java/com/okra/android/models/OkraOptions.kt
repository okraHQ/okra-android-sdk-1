package com.okra.android.models

import java.util.*

class OkraOptions private constructor(val key: String?,
                                      val app_id: String?,
                                      val token: String?,
                                      val products: Array<String>?,
                                      val env: String?,
                                      val name: String?,
                                      val short_url: String?,
                                      val payment: Boolean?,
                                      val charge: Any?,
                                      val logo: String?,
                                      val color: String?,
                                      val limit: Int?,
                                      val filter: Any?,
                                      val isCorporate: Boolean?,
                                      val connectMessage: String?,
                                      val widget_success: String?,
                                      val widget_failed: String?,
                                      val callback_url: String?,
                                      val currency: String?,
                                      val exp: Date?,
                                      val options: Any?,
                                      ){

    data class Builder(var key: String? = null,
                       var appId: String? = null,
                       var token: String?= null,
                       var products: Array<String>? = null,
                       var env: String? = null,
                       var name: String? = null,
                       var short_url: String? = null,
                       var payment: Boolean? = null,
                       var charge: Any? = null,
                       var logo: String? = null,
                       var color: String? = null,
                       var limit: Int? = null,
                       var filter: Any? = null,
                       var isCorporate: Boolean? = null,
                       var connectMessage: String? = null,
                       var widgetSuccess: String? = null,
                       var widgetFailed: String? = null,
                       var callbackurl: String? = null,
                       var currency: String? = null,
                       var exp: Date? = null,
                       var options: Any? = null,

    ){
        fun key(key: String) = apply { this.key = key }
        fun appId(appId: String) = apply { this.appId = appId }
        fun env(env: String) = apply { this.env = env }
        fun token(token: String) = apply { this.token = token }
        fun products(products: Array<String>?) = apply { this.products = products }
        fun name(name: String) = apply { this.name = name }
        fun shortUrl(shortUrl: String) = apply { this.short_url = shortUrl }
        fun payment(payment: Boolean) = apply { this.payment = payment }
        fun charge(charge: Any?) = apply { this.charge = charge }
        fun logo(logo: String?) = apply { this.logo = logo }
        fun color(color: String?) = apply { this.color = color }
        fun limit(limit: Int?) = apply { this.limit = limit }
        fun filter(filter: Any?) = apply { this.filter = filter }
        fun isCorporate(isCorporate: Boolean?) = apply { this.isCorporate = isCorporate }
        fun connectMessage(connectMessage: String?) = apply { this.connectMessage = connectMessage }
        fun widgetSuccess(widgetSuccess: String?) = apply { this.widgetSuccess = widgetSuccess }
        fun widgetFailed(widgetFailed: String?) = apply { this.widgetFailed = widgetFailed }
        fun callbackUrl(callbackurl: String?) = apply { this.callbackurl = callbackurl }
        fun currency(currency: String?) = apply { this.currency = currency }
        fun exp(exp: Date?) = apply { this.exp = exp }
        fun options(options: Any?) = apply { this.options = options }
        fun build() = OkraOptions(key,appId, token, products, env, name, short_url,payment,charge,logo,color,limit,filter, isCorporate,connectMessage,widgetSuccess,widgetFailed,callbackurl,currency, exp, options)
    }
}
