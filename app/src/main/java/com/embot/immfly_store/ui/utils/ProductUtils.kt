package com.embot.immfly_store.ui.utils

import com.embot.immfly_store.domain.models.apiModel.ApiProduct
import com.embot.immfly_store.domain.models.appModel.ProductModel

object ProductUtils {

    fun parseToAppProduct(apiProducts: List<ApiProduct>): List<ProductModel> {
        return apiProducts.map { apiProduct ->
            ProductModel(
                id = apiProduct.id,
                name = apiProduct.name,
                description = apiProduct.description,
                price = apiProduct.price,
                imageUrl = apiProduct.image,
            )
        }
    }

}