package com.embot.immfly_store.domain.models.appModel

import java.math.BigDecimal
import java.util.Currency
import java.util.Locale

data class Price(
    val price: BigDecimal,
    val currency: Currency,
    val locale: Locale,
)