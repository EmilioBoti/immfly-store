package com.embot.immfly_store.ui.features.productList.repository

import com.embot.immfly_store.domain.models.apiModel.ApiCurrencyRate
import com.embot.immfly_store.domain.models.apiModel.ApiProduct
import com.embot.immfly_store.domain.models.roomEntity.ProductEntity
import com.embot.immfly_store.domain.service.DispatchersProvider
import com.embot.immfly_store.domain.service.localResource.localDatabase.ProductDao
import com.embot.immfly_store.domain.service.remoteSource.IProductService
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductListRepository @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val localDatabase: ProductDao,
    private val productService: IProductService
): IProductListRepository {

    override suspend fun getAllProducts(): Result<List<ApiProduct>> {
        return withContext(dispatchersProvider.io) {
            try {
                val result = productService.getAllProducts()
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

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

    override suspend fun getAllCartProduct(): Result<List<ProductEntity>> {
        return withContext(dispatchersProvider.io) {
            try {
                val products = localDatabase.getCartProducts()
                Result.success(products)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun addProductToCart(productEntity: ProductEntity): Result<Boolean> {
        return withContext(dispatchersProvider.io) {
            try {
                localDatabase.storeToCart(productEntity)
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun removeProductFromCart(productEntity: ProductEntity): Result<Boolean> {
        return withContext(dispatchersProvider.io) {
            try {
                localDatabase.deleteFromCart(productEntity)
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}