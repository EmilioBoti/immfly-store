package com.embot.immfly_store.ui.utils

import com.embot.immfly_store.domain.useCase.Price
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

object CurrencyFormatter {


    fun formatCurrencyPrice(price: Price): String {
        return formatCurrency(price.price, price.currency.currencyCode, price.locale)
    }

    fun formatCurrency(amount: BigDecimal, currencyCode: String, locale: Locale): String {
        val currencyFormat = NumberFormat.getCurrencyInstance(locale)
        currencyFormat.currency = Currency.getInstance(currencyCode)
        return  "${currencyFormat.currency?.symbol ?: ""} $amount"
    }

}