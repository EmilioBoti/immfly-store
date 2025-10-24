package com.embot.immfly_store.domain.service.remoteSource

import com.embot.immfly_store.domain.models.apiModel.ApiCurrencyRate
import com.embot.immfly_store.domain.models.apiModel.ApiPayOrder
import com.embot.immfly_store.domain.models.apiModel.ApiPayment
import com.embot.immfly_store.domain.models.apiModel.ApiProduct
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IProductService {

    @GET("/products")
    suspend fun getAllProducts(): ArrayList<ApiProduct>

    @GET("/currencyRates")
    suspend fun getCurrencyRates(): ApiCurrencyRate

    @POST("/payment")
    suspend fun paymentOrdet(
        @Body payment: List<ApiPayOrder>
    ): ApiPayment

}