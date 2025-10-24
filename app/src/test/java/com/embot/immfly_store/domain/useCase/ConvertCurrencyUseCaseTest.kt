package com.embot.immfly_store.domain.useCase

import com.embot.immfly_store.domain.models.appModel.CurrencyRateModel
import com.embot.immfly_store.domain.models.constants.CurrencyType
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.Currency
import com.google.common.truth.Truth.assertThat
import java.util.Locale


class ConvertCurrencyUseCaseTest {


    private var useCase = ConvertCurrencyUseCase()

    @Before
    fun setup() {
        useCase.setCurrencyRate(CurrencyRateModel(
            id = CurrencyType.EUR.type,
            base = Currency.getInstance(CurrencyType.EUR.type),
            rates = mapOf(
                Currency.getInstance(CurrencyType.EUR.type) to BigDecimal("1.0"),
                Currency.getInstance(CurrencyType.USD.type) to BigDecimal("1.16050"),
                Currency.getInstance(CurrencyType.GBP.type) to BigDecimal("0.867862")
            )
        ))
    }


    @Test
    fun `convert price to selected base currency`() {
        //GIVEN
        val amount = "699.00"
        //WHEN
        val convertedPrice = useCase.getSelectedCurrencyPrice(price = amount,  toCurrency = CurrencyType.USD.type)
        //THEN
        assertThat(convertedPrice.price).isEqualTo(BigDecimal("811.19"))

    }

    @Test
    fun `test convertCurrency from one to another`() {
        //GIVEN
        val amount = BigDecimal("699.00")

        //WHEN
        val converted = useCase.convert(
            amount = amount,
            fromCurrency = CurrencyType.EUR.type,
            toCurrency = CurrencyType.GBP.type,
        )
        //THEN
        assertThat(converted).isEqualTo(BigDecimal("606.64"))
    }

    @Test
    fun `test convertCurrency`() {
        //GIVEN
        val amount = BigDecimal("699.00")

        //WHEN
        val converted = useCase.convert(
            amount = amount,
            rate = BigDecimal("1.16050")
        )
        //THEN
        assertThat(converted).isEqualTo(BigDecimal("811.19"))
    }

    @Test
    fun `return currency locale`() {
        //GIVEN
        val currency = CurrencyType.GBP.type

        //WHEN
        val locale = useCase.getLocaleCurrency(currency)

        //THEN
        assertThat(locale).isEqualTo(Locale.UK)

    }

    @Test
    fun `return default currency locale if unknown currenry type`() {
        //GIVEN
        val currency = "SD"

        //WHEN
        val locale = useCase.getLocaleCurrency(currency)

        //THEN
        assertThat(locale).isEqualTo(Locale.GERMANY)

    }


}