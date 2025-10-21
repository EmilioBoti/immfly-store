package com.embot.immfly_store.domain.models.constants

import com.embot.immfly_store.R


enum class CurrencyType(
    val type: String,
    val currencyName: Int
) {
    EUR(type = "EUR", currencyName = R.string.current_name_euro),
    USD(type = "USD", currencyName = R.string.current_name_dollar),
    GBP(type = "GBP", currencyName = R.string.current_name_pound)
}