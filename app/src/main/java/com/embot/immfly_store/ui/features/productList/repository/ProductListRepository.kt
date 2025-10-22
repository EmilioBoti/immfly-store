package com.embot.immfly_store.ui.features.productList.repository

import com.embot.immfly_store.domain.models.apiModel.ApiCurrencyRate
import com.embot.immfly_store.domain.models.apiModel.ApiProduct
import com.embot.immfly_store.domain.models.roomEntity.ProductEntity
import com.embot.immfly_store.domain.service.IProductService
import com.embot.immfly_store.domain.service.localResource.localDatabase.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductListRepository @Inject constructor(
    private val localDatabase: ProductDao,
    private val productService: IProductService
): IProductListRepository {

    override suspend fun getAllProducts(): Result<List<ApiProduct>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = productService.getAllProducts()
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

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

    override suspend fun getAllCartProduct(): Result<List<ProductEntity>> {
        return withContext(Dispatchers.IO) {
            try {
                val products = localDatabase.getCartProducts()
                Result.success(products)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun addProductToCart(productEntity: ProductEntity): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                localDatabase.storeToCart(productEntity)
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun removeProductFromCart(productEntity: ProductEntity): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                localDatabase.deleteFromCart(productEntity)
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}