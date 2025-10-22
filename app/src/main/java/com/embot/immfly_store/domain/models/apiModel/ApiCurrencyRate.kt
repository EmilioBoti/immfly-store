package com.embot.immfly_store.domain.models.apiModel

import com.google.gson.annotations.SerializedName

data class ApiCurrencyRate(
    @SerializedName("id") val id: String,
    @SerializedName("base") val base: String,
    @SerializedName("rates") val rates: Map<String, String>
)
