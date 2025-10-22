package com.embot.immfly_store.domain.service.localResource.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.embot.immfly_store.domain.models.constants.CurrencyType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val DATA_STORE_NAME: String = "DATA_STORE_NAME_IMMFLY"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)


class AppDataStore @Inject constructor(
    @param:ApplicationContext private val context: Context
): IAppDataStore {

    companion object {

        val CURRENCY_KEY: Preferences.Key<String> = stringPreferencesKey("CURRENCY_KEY")

    }

    override suspend fun saveCurrencyType(currencyType: CurrencyType) {
        context.dataStore.edit { preferences -> preferences[CURRENCY_KEY] = currencyType.type }
    }

    override suspend fun getCurrencyType(): Flow<CurrencyType> {
        return withContext(Dispatchers.IO) {
            context.dataStore.data.map { preferences ->
                when(preferences[CURRENCY_KEY]) {
                    CurrencyType.USD.type -> CurrencyType.USD
                    CurrencyType.EUR.type -> CurrencyType.EUR
                    CurrencyType.GBP.type -> CurrencyType.GBP
                    else -> CurrencyType.EUR
                }
            }
        }
    }

}