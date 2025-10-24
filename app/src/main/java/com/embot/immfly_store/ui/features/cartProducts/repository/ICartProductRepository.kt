package com.embot.immfly_store.ui.features.cartProducts.repository

import com.embot.immfly_store.domain.models.apiModel.ApiCurrencyRate
import com.embot.immfly_store.domain.models.apiModel.ApiPayOrder
import com.embot.immfly_store.domain.models.apiModel.ApiPayment
import com.embot.immfly_store.domain.models.roomEntity.ProductEntity


interface ICartProductRepository {

    suspend fun getCurrencyRate(): Result<ApiCurrencyRate>
    suspend fun getAllCartProducts(): Result<List<ProductEntity>>
    suspend fun updateProductQuantity(id: String, quantity: Int): Result<Boolean>
    suspend fun deleteFromCart(id: String): Result<Boolean>
    suspend fun clearCart(): Result<Boolean>
    suspend fun payOrder(order: List<ApiPayOrder>): Result<ApiPayment>

}
