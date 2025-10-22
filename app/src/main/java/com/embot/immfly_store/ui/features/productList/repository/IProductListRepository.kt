package com.embot.immfly_store.ui.features.productList.repository

import com.embot.immfly_store.domain.models.apiModel.ApiCurrencyRate
import com.embot.immfly_store.domain.models.apiModel.ApiProduct
import com.embot.immfly_store.domain.models.roomEntity.ProductEntity

interface IProductListRepository {
    suspend fun getAllProducts(): Result<List<ApiProduct>>
    suspend fun getCurrencyRate(): Result<ApiCurrencyRate>
    suspend fun getAllCartProduct(): Result<List<ProductEntity>>
    suspend fun addProductToCart(productEntity: ProductEntity): Result<Boolean>
    suspend fun removeProductFromCart(productEntity: ProductEntity): Result<Boolean>
}
