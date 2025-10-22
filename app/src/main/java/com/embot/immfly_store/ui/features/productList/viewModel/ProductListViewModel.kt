package com.embot.immfly_store.ui.features.productList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.embot.immfly_store.domain.models.appModel.ProductModel
import com.embot.immfly_store.domain.models.constants.CurrencyType
import com.embot.immfly_store.domain.models.uiState.ProductItemState
import com.embot.immfly_store.domain.service.localResource.preference.IAppDataStore
import com.embot.immfly_store.domain.useCase.ConvertCurrencyUseCase
import com.embot.immfly_store.ui.features.productList.repository.IProductListRepository
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
import java.math.BigDecimal
import javax.inject.Inject


@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val preferences: IAppDataStore,
    private val currencyUseCase: ConvertCurrencyUseCase,
    private val productRepository: IProductListRepository
): ViewModel() {

    private var currentCurrencyType: CurrencyType = CurrencyType.EUR
    private val _product: MutableStateFlow<List<ProductItemState>> = MutableStateFlow(listOf())
    val product: StateFlow<List<ProductItemState>> = _product.onStart {
        getAllProducts()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )


    fun updateCurrencyType(currencyType: CurrencyType) {
        currentCurrencyType = currencyType
        udpateProductCurrenry(currentCurrencyType)
    }

    fun getAllProducts() {
        viewModelScope.launch {
            try {
                currentCurrencyType = preferences.getCurrencyType().first()

                val (currencyResponse, productResponse) = coroutineScope {
                    val currencyDeferred = async { productRepository.getCurrencyRate() }
                    val productDeferred = async { productRepository.getAllProducts() }

                    currencyDeferred.await() to productDeferred.await()
                }

                currencyResponse.onSuccess {
                    currencyUseCase.setCurrencyRate(ProductUtils.toAppCurrencyRate(it))
                }

                productResponse.onSuccess { apiProducts ->
                    val products = ProductUtils.parseToAppProduct(apiProducts)
                    _product.update { parseToProdcutState(products) }
                }.onFailure {
                    print(it.message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun parseToProdcutState(products: List<ProductModel>): List<ProductItemState> {
        return products.map { prod ->
            ProductItemState(
                id = prod.id,
                name = prod.name,
                description = prod.description,
                price = CurrencyFormatter.formatCurrencyPrice(currencyUseCase.getSelectedCurrencyPrice("${prod.price}", currentCurrencyType.type)),
                rawPrice = "${prod.price}",
                currencies = getConvertedValues(BigDecimal("${prod.price}")),
                imageUrl = prod.imageUrl,
                stock = prod.stock,
                isBucketed = false
            )
        }
    }

    fun udpateProductCurrenry(currencyType: CurrencyType) {
        _product.update {
            it.map { prod ->
                prod.copy(
                    price = CurrencyFormatter.formatCurrencyPrice(currencyUseCase.getSelectedCurrencyPrice(prod.rawPrice, currencyType.type)),
                    currencies = getConvertedValues(BigDecimal(prod.rawPrice))
                )
            }
        }
    }

    fun getConvertedValues(amount: BigDecimal): List<String> {
        val currencies = currencyUseCase.getConvertedValues(amount, currentCurrencyType.type)
        return currencies.mapValues { (target, value) ->
            val locale = currencyUseCase.getLocaleCurrency(target?.currencyCode ?: "")
            CurrencyFormatter.formatCurrency(value, currencyCode = target?.currencyCode ?: "", locale)
        }.map { it.value }
    }

}