package com.embot.immfly_store.domain.useCase

import com.embot.immfly_store.domain.models.appModel.CurrencyRateModel
import com.embot.immfly_store.domain.models.appModel.Price
import com.embot.immfly_store.domain.models.constants.CurrencyType
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Currency
import java.util.Locale
import javax.inject.Inject


class ConvertCurrencyUseCase @Inject constructor() {

    private var currencyRate: CurrencyRateModel = CurrencyRateModel(
        id = CurrencyType.EUR.type,
        base = Currency.getInstance(CurrencyType.EUR.type),
        rates = mapOf()
    )


    fun setCurrencyRate(currencyRate: CurrencyRateModel) {
        this.currencyRate = currencyRate
    }

    fun getConvertedValues(
        amount: BigDecimal,
        toCurrency: String
    ): Map<Currency?, BigDecimal> {
        return currencyRate.rates.mapValues { (target, rate) ->
            convert(amount, rate)
        }.filter { it.key?.currencyCode !== getCurrency(toCurrency).currencyCode }
    }

    /**
     *
     */
    fun getSelectedCurrencyPrice(
        price: String,
        toCurrency: String,
    ): Price {
        return Price(
            price = convert(
                amount = BigDecimal(price),
                fromCurrency = currencyRate.base.currencyCode,
                toCurrency = toCurrency
            ),
            currency = getCurrency(toCurrency),
            locale = getLocaleCurrency(toCurrency)
        )
    }

    /**
     * Converts an amount between two currencies with precision using BigDecimal.
     *
     * @param amount amount to convert
     * @param fromCurrency currency code of the source
     * @param toCurrency currency code of the target
     */
    fun convert(
        amount: BigDecimal,
        fromCurrency: String,
        toCurrency: String
    ): BigDecimal {
        val fromRate = currencyRate.rates[getCurrency(fromCurrency)]
            ?: BigDecimal.ONE
        val toRate = currencyRate.rates[getCurrency(toCurrency)]
            ?: BigDecimal.ONE

        // toRate / fromRate
        val conversionRate = toRate.divide(fromRate, 10, RoundingMode.HALF_EVEN)

        // amount × conversionRate, scaled properly
        return amount.multiply(conversionRate).setScale(2, RoundingMode.HALF_EVEN)
    }

    fun convert(
        amount: BigDecimal,
        rate: BigDecimal
    ): BigDecimal {
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_EVEN)
    }


    fun getCurrency(baseCurrency: String): Currency {
        val productLocale = getLocaleCurrency(baseCurrency)
        return Currency.getInstance(productLocale)
    }

    fun getLocaleCurrency(baseCurrency: String): Locale {
        return when (baseCurrency) {
            CurrencyType.EUR.type -> Locale.GERMANY
            CurrencyType.USD.type -> Locale.US
            CurrencyType.GBP.type -> Locale.UK
            else -> Locale.GERMANY
        }
    }


}