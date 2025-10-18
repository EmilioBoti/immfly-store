package com.embot.immfly_store.domain.service

import com.embot.immfly_store.domain.models.apiModel.ApiProduct
import retrofit2.http.GET


interface IProductService {

    @GET("/products")
    suspend fun getAllProducts(): ArrayList<ApiProduct>

}