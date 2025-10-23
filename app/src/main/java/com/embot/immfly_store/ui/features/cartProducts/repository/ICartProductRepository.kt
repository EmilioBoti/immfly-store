package com.embot.immfly_store.ui.features.cartProducts.repository

import com.embot.immfly_store.domain.models.apiModel.ApiCurrencyRate
import com.embot.immfly_store.domain.models.roomEntity.ProductEntity


interface ICartProductRepository {

    suspend fun getCurrencyRate(): Result<ApiCurrencyRate>
    suspend fun getAllCartProducts(): Result<List<ProductEntity>>

}
