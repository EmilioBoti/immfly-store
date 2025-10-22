package com.embot.immfly_store.ui.features.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.embot.immfly_store.domain.models.constants.CurrencyType
import com.embot.immfly_store.domain.service.localResource.preference.IAppDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val preferences: IAppDataStore
): ViewModel() {

    private val _isCurrencyPickerOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCurrencyPickerOpen: StateFlow<Boolean> = _isCurrencyPickerOpen.asStateFlow()

    private val _selectedCurrency: MutableStateFlow<CurrencyType> = MutableStateFlow(CurrencyType.EUR)
    val selectedCurrency = _selectedCurrency.onStart {
        getCurrencySelected()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CurrencyType.EUR
    )


    private fun getCurrencySelected() {
        viewModelScope.launch {
            preferences.getCurrencyType().collectLatest { currencyType ->
                _selectedCurrency.update { currencyType }
            }
        }
    }

    fun setCurrencySelected(currencyType: CurrencyType) {
        viewModelScope.launch {
            preferences.saveCurrencyType(currencyType)
            _selectedCurrency.update { currencyType }
            openCurrencyPicker()
        }
    }

    fun openCurrencyPicker() {
        _isCurrencyPickerOpen.update { !it }
    }


}