package com.embot.immfly_store.ui.features.productList.repository

import com.embot.immfly_store.domain.models.apiModel.ApiCurrencyRate
import com.embot.immfly_store.domain.models.apiModel.ApiProduct
import com.embot.immfly_store.domain.service.IProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductListRepository @Inject constructor(
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

}