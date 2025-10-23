package com.embot.immfly_store.ui.features.cartProducts.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.embot.immfly_store.domain.models.constants.CurrencyType
import com.embot.immfly_store.domain.models.roomEntity.ProductEntity
import com.embot.immfly_store.domain.models.uiState.CartItemState
import com.embot.immfly_store.domain.service.localResource.preference.IAppDataStore
import com.embot.immfly_store.domain.useCase.ConvertCurrencyUseCase
import com.embot.immfly_store.ui.features.cartProducts.repository.ICartProductRepository
import com.embot.immfly_store.ui.utils.CurrencyFormatter
import com.embot.immfly_store.ui.utils.ProductUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartProductViewModel @Inject constructor(
    private val currencyUseCase: ConvertCurrencyUseCase,
    private val prefereces: IAppDataStore,
    private val cartRepository: ICartProductRepository
): ViewModel() {

    private var currency: CurrencyType = CurrencyType.EUR
    private val _products: MutableStateFlow<List<CartItemState>> = MutableStateFlow(listOf())
    val products: StateFlow<List<CartItemState>> = _products.onStart {
        loadData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )


    private fun loadData() {
        viewModelScope.launch {
            try {
                currency = prefereces.getCurrencyType().first()

                val (currencyRate, products) = coroutineScope {
                    val currencyRateDeferred = async { cartRepository.getCurrencyRate() }
                    val productsDeferred = async { cartRepository.getAllCartProducts() }
                    currencyRateDeferred.await() to productsDeferred.await()
                }

                currencyRate.onSuccess { currencyUseCase.setCurrencyRate(ProductUtils.toAppCurrencyRate(it)) }

                products.onSuccess { cartItems ->
                    _products.update { parseToCartItemState(cartItems) }
                }
                products.onFailure {
                    Log.e("error", it.stackTraceToString())
                }

            } catch (e: Exception) {

            }
        }
    }

    private fun parseToCartItemState(cartItems: List<ProductEntity>): List<CartItemState> {
        return cartItems.map {
            CartItemState(
                id = it.id,
                name = it.name,
                price = CurrencyFormatter.formatCurrencyPrice(currencyUseCase.getSelectedCurrencyPrice("${it.price}", currency.type)),
                image = it.image,
                realPrice = it.price,
                cuantity = it.cuantity,
                stock = it.stock,
            )
        }
    }



}