package com.embot.immfly_store.ui.features.productList.repository

import com.embot.immfly_store.domain.models.apiModel.ApiProduct

interface IProductListRepository {
    suspend fun getAllProducts(): Result<List<ApiProduct>>
}
