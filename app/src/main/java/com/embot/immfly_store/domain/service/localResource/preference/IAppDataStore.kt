package com.embot.immfly_store.domain.service.localResource.preference

import com.embot.immfly_store.domain.models.constants.CurrencyType
import kotlinx.coroutines.flow.Flow

interface IAppDataStore {

    suspend fun saveCurrencyType(currencyType: CurrencyType)
    suspend fun getCurrencyType(): Flow<CurrencyType>


}
