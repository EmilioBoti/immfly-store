package com.embot.immfly_store.ui.features.cartProducts.repository

import com.embot.immfly_store.domain.models.apiModel.ApiCurrencyRate
import com.embot.immfly_store.domain.models.apiModel.ApiPayOrder
import com.embot.immfly_store.domain.models.apiModel.ApiPayment
import com.embot.immfly_store.domain.models.roomEntity.ProductEntity
import com.embot.immfly_store.domain.service.DispatchersProvider
import com.embot.immfly_store.domain.service.remoteSource.IProductService
import com.embot.immfly_store.domain.service.localResource.localDatabase.ProductDao
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CartProductRepository @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val localDatabase: ProductDao,
    private val productService: IProductService
): ICartProductRepository {


    override suspend fun getCurrencyRate(): Result<ApiCurrencyRate> {
        return withContext(dispatchersProvider.io) {
            try {
                val result = productService.getCurrencyRates()
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getAllCartProducts(): Result<List<ProductEntity>> {
        return withContext(dispatchersProvider.io) {
            try {
                val result = localDatabase.getCartProducts()
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun updateProductQuantity(id: String, quantity: Int): Result<Boolean> {
        return withContext(dispatchersProvider.io) {
            try {
                localDatabase.updateProductQuantity(id, quantity)
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun deleteFromCart(id: String): Result<Boolean> {
        return withContext(dispatchersProvider.io) {
            try {
                localDatabase.deleteFromCart(id)
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun clearCart(): Result<Boolean> {
        return withContext(dispatchersProvider.io) {
            try {
                val result = localDatabase.clearCart()
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun payOrder(order: List<ApiPayOrder>): Result<ApiPayment> {
        return withContext(dispatchersProvider.io) {
            try {
                val result = productService.paymentOrdet(order)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}