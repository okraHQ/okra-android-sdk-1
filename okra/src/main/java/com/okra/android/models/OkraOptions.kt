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
    val exp: String?= null,
    val meta: Any?= null,
    val options: Map<String, Any>? = null,
    val reAuthBankSlug: String? = null,
    val reAuthAccountNumber: String? = null,
    val customerId: String? = null,
    val customerBvn: String? = null,
    val customerNin: String? = null,
    val customerPhone: String? = null,
    val customerEmail: String? = null,
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
        private var exp: String? = "",
        private var meta: Any? = null,
        private var options: Map<String, Any>? = null,
        private var reAuthAccountNumber: String? = "",
        private var reAuthBankSlug: String? = "",
        private var customerBvn: String? = "",
        private var customerNin: String? = "",
        private var customerId: String? = "",
        private var customerEmail: String? = "",
        private var customerPhone: String? = "",
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
        fun exp(exp: String?) = apply { this.exp = exp }
        fun meta(meta: Any?) = apply { this.meta = meta }
        fun options(options: Map<String, Any>?) = apply { this.options = options }
        fun reAuthAccountNumber(reAuthAccountNumber: String?) = apply { this.reAuthAccountNumber = reAuthAccountNumber }
        fun reAuthBankSlug(reAuthBankSlug: String?) = apply { this.reAuthBankSlug = reAuthBankSlug }
        fun customerId(customerId: String?) = apply { this.customerId = customerId }
        fun customerBvn(customerBvn: String?) = apply { this.customerBvn = customerBvn }
        fun customerNin(customerNin: String?) = apply { this.customerNin = customerNin }
        fun customerPhone(customerPhone: String?) = apply { this.customerPhone = customerPhone }
        fun customerEmail(customerEmail: String?) = apply { this.customerEmail = customerEmail }
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
            meta,
            options,
            reAuthAccountNumber,
            reAuthBankSlug,
            customerBvn,
            customerId,
            customerNin,
            customerPhone,
            customerEmail,
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
