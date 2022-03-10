package com.okra.android.models

import java.util.*
import kotlin.collections.ArrayList

class OkraOptions private constructor(
    val key: String? = null,
    val app_id: String? = null,
    val token: String? = null,
    val products: ArrayList<String>? = null,
    val env: String? = null,
    val name: String?= null,
    val short_url: String?= null,
    val payment: Boolean?= null,
    val charge: Charge?= null,
    val logo: String?= null,
    val color: String?= null,
    val limit: Int?= null,
    val filter: Any?= null,
    val isCorporate: Boolean?= null,
    val connectMessage: String?= null,
    val widget_success: String?= null,
    val widget_failed: String?= null,
    val callback_url: String?= null,
    val currency: String?= null,
    val exp: Date?= null,
    val options: Any?= null,
) {

    data class OptionsBuilder(
        private var key: String,
        private var token: String,
        private var env: String,
        private var name: String,
        private var products: ArrayList<String>,
        private var appId: String? = "",
        private var payment: Boolean = false,
        private var charge: Charge? = Charge("","","",""),
        private var logo: String? = "",
        private var color: String? = "#3AB795",
        private var limit: Int? = 24,
        private var filter: Any? = null,
        private var isCorporate: Boolean = false,
        private var connectMessage: String? = "",
        private var widgetSuccess: String? = "",
        private var widgetFailed: String? = "",
        private var callbackurl: String? = "",
        private var currency: String? = "",
        private var exp: Date? = Date(),
        private var options: Any? = null,
    )
    {

        fun appId(appId: String) = apply { this.appId = appId }
        fun payment(payment: Boolean) = apply { this.payment = payment }
        fun charge(charge: Charge?) = apply { this.charge = charge }
        fun logo(logo: String?) = apply { this.logo = logo }
        fun color(color: String?) = apply { this.color = color }
        fun limit(limit: Int?) = apply { this.limit = limit }
        fun filter(filter: Any?) = apply { this.filter = filter }
        fun isCorporate(isCorporate: Boolean) = apply { this.isCorporate = isCorporate }
        fun connectMessage(connectMessage: String?) = apply { this.connectMessage = connectMessage }
        fun widgetSuccess(widgetSuccess: String?) = apply { this.widgetSuccess = widgetSuccess }
        fun widgetFailed(widgetFailed: String?) = apply { this.widgetFailed = widgetFailed }
        fun callbackUrl(callbackurl: String?) = apply { this.callbackurl = callbackurl }
        fun currency(currency: String?) = apply { this.currency = currency }
        fun exp(exp: Date?) = apply { this.exp = exp }
        fun options(options: Any?) = apply { this.options = options }
        fun build() = OkraOptions(
            key,
            appId,
            token,
            products,
            env,
            name,
            null,
            payment,
            charge,
            logo,
            color,
            limit,
            filter,
            isCorporate,
            connectMessage,
            widgetSuccess,
            widgetFailed,
            callbackurl,
            currency,
            exp,
            options
        )


    }
    data class ShortUrlBuilder(
        private var short_url: String,
    )
    {
        fun build() = OkraOptions(
            null,
            null,
            null,
            null,
            null,
            null,
            short_url,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }
}
