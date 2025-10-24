package com.embot.immfly_store.domain.models.apiModel

import com.google.gson.annotations.SerializedName

data class ApiPayment(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)