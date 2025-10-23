package com.embot.immfly_store.ui.features.cartProducts.repository

import com.embot.immfly_store.domain.models.apiModel.ApiCurrencyRate
import com.embot.immfly_store.domain.models.roomEntity.ProductEntity
import com.embot.immfly_store.domain.service.IProductService
import com.embot.immfly_store.domain.service.localResource.localDatabase.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CartProductRepository @Inject constructor(
    private val localDatabase: ProductDao,
    private val productService: IProductService
): ICartProductRepository {


    override suspend fun getCurrencyRate(): Result<ApiCurrencyRate> {
        return withContext(Dispatchers.IO) {
            try {
                val result = productService.getCurrencyRates()
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getAllCartProducts(): Result<List<ProductEntity>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = localDatabase.getCartProducts()
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun updateProductQuantity(id: String, quantity: Int): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                localDatabase.updateProductQuantity(id, quantity)
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}