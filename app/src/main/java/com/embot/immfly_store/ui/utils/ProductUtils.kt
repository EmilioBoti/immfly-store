package com.embot.immfly_store.ui.utils

import com.embot.immfly_store.domain.models.apiModel.ApiCurrencyRate
import com.embot.immfly_store.domain.models.apiModel.ApiProduct
import com.embot.immfly_store.domain.models.appModel.CurrencyRateModel
import com.embot.immfly_store.domain.models.appModel.ProductModel
import java.math.BigDecimal
import java.util.Currency

object ProductUtils {

    fun parseToAppProduct(apiProducts: List<ApiProduct>): List<ProductModel> {
        return apiProducts.map { apiProduct ->
            ProductModel(
                id = apiProduct.id,
                name = apiProduct.name,
                description = apiProduct.description,
                price = apiProduct.price,
                imageUrl = apiProduct.image,
                stock = apiProduct.stock
            )
        }
    }

    fun toAppCurrencyRate(apiCurrencyRate: ApiCurrencyRate): CurrencyRateModel {
        return CurrencyRateModel(
            id = apiCurrencyRate.id,
            base = Currency.getInstance(apiCurrencyRate.base),
            rates = apiCurrencyRate.rates.map { (key, value) -> Currency.getInstance(key) to BigDecimal(value) }.toMap()
        )
    }

}