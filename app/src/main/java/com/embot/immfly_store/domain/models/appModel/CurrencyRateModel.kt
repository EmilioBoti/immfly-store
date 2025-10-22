package com.embot.immfly_store.domain.models.appModel

import java.math.BigDecimal
import java.util.Currency

data class CurrencyRateModel(
    val id: String,
    val base: Currency,
    val rates: Map<Currency?, BigDecimal>
)
