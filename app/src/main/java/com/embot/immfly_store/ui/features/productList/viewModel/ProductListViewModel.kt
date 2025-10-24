package com.embot.immfly_store.ui.features.productList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.embot.immfly_store.domain.models.appModel.ProductModel
import com.embot.immfly_store.domain.models.constants.CurrencyType
import com.embot.immfly_store.domain.models.roomEntity.ProductEntity
import com.embot.immfly_store.domain.models.uiState.ErrorState
import com.embot.immfly_store.domain.models.uiState.GenericState
import com.embot.immfly_store.domain.models.uiState.ProductItemState
import com.embot.immfly_store.domain.models.uiState.ProductListState
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
import retrofit2.HttpException
import java.io.IOException
import java.math.BigDecimal
import javax.inject.Inject


@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val preferences: IAppDataStore,
    private val currencyUseCase: ConvertCurrencyUseCase,
    private val productRepository: IProductListRepository
): ViewModel() {

    private var currentCurrencyType: CurrencyType = CurrencyType.EUR
    private var cartProducts: ArrayList<ProductEntity> = arrayListOf()

    private val _productsState: MutableStateFlow<ProductListState> = MutableStateFlow(ProductListState(GenericState(), products = listOf()))
    val productsState: StateFlow<ProductListState> = _productsState.onStart {
        getAllProducts()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 1000),
        initialValue = ProductListState(GenericState(), products = listOf())
    )


    fun updateCurrencyType(currencyType: CurrencyType) {
        currentCurrencyType = currencyType
        udpateProductCurrenry(currentCurrencyType)
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            isLoading(true)
            try {
                currentCurrencyType = preferences.getCurrencyType().first()

                val (currencyResponse, productResponse, cartProducts) = coroutineScope {
                    val cartProductsDeferred = async { productRepository.getAllCartProduct() }
                    val currencyDeferred = async { productRepository.getCurrencyRate() }
                    val productDeferred = async { productRepository.getAllProducts() }

                    Triple(
                        first = currencyDeferred.await(),
                        second = productDeferred.await(),
                        third = cartProductsDeferred.await()
                    )
                }

                cartProducts.onSuccess { cart ->
                    this@ProductListViewModel.cartProducts.apply {
                        this.clear()
                        this.addAll(cart)
                    }
                }

                currencyResponse.onSuccess {
                    currencyUseCase.setCurrencyRate(ProductUtils.toAppCurrencyRate(it))
                }

                productResponse.onSuccess { apiProducts ->
                    val products = ProductUtils.parseToAppProduct(apiProducts)
                    _productsState.update {
                        it.copy(
                            products = parseToProdcutState(products, this@ProductListViewModel.cartProducts)
                        )
                    }
                }.onFailure { e ->
                    handleError(true, getError(e))
                }
            } catch (e: Exception) {
                handleError( true, getError(e))
            } finally {
                isLoading(false)
            }
        }
    }

    private fun getError(e: Throwable): ErrorState {
        return when (e) {
            is IOException -> ErrorState.NetworkError
            is HttpException -> ErrorState.NetworkError
            else -> ErrorState.UnknownError
        }
    }

    private fun handleError(isError: Boolean, errorState: ErrorState) {
        _productsState.update {
            it.copy(
                error =it.error.copy(
                    isError = isError,
                    error = errorState
                )
            )
        }
    }

    private fun isLoading(isLoading: Boolean) {
        _productsState.update {
            it.copy(
                error =it.error.copy(
                    isLoading = isLoading
                )
            )
        }
    }

    private fun storeProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            val res = productRepository.addProductToCart(productEntity)
            res.onSuccess {
                cartProducts.add(productEntity)
                updateProduct()
            }
            res.onFailure { e -> handleError(true, getError(e)) }
        }
    }

    private fun removeProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            val res = productRepository.removeProductFromCart(productEntity)
            res.onSuccess {
                cartProducts.remove(productEntity)
                updateProduct()
            }
            res.onFailure { e -> handleError(true, getError(e)) }
        }
    }

    private fun parseToProdcutState(
        products: List<ProductModel>,
        cartProducts: ArrayList<ProductEntity>
    ): List<ProductItemState> {
        return products.map { prod ->
            ProductItemState(
                id = prod.id,
                name = prod.name,
                description = prod.description,
                price = CurrencyFormatter.formatCurrencyPrice(currencyUseCase.getSelectedCurrencyPrice("${prod.price}", currentCurrencyType.type)),
                rawPrice = prod.price,
                currencies = getConvertedValues(BigDecimal("${prod.price}")),
                imageUrl = prod.imageUrl,
                stock = prod.stock,
                isBucketed = isProdcutICart(prod.id, cartProducts)
            )
        }
    }

    private fun isProdcutICart(prodId: String, cartProducts: ArrayList<ProductEntity>): Boolean {
        return cartProducts.firstOrNull { it.id == prodId } != null
    }

    private fun updateProduct() {
        _productsState.update { state ->
            state.copy(
                products = state.products.map { prod ->
                    prod.copy(
                    isBucketed = isProdcutICart(prod.id, cartProducts)
                    )
                }
            )
        }
    }

    private fun udpateProductCurrenry(currencyType: CurrencyType) {
        _productsState.update { state ->
            state.copy(
                products = state.products.map { prod ->
                    prod.copy(
                        price = CurrencyFormatter.formatCurrencyPrice(currencyUseCase.getSelectedCurrencyPrice(prod.rawPrice.toString(), currencyType.type)),
                        currencies = getConvertedValues(BigDecimal(prod.rawPrice))
                    )
                }
            )
        }
    }

    private fun getConvertedValues(amount: BigDecimal): List<String> {
        val currencies = currencyUseCase.getConvertedValues(amount, currentCurrencyType.type)
        return currencies.mapValues { (target, value) ->
            val locale = currencyUseCase.getLocaleCurrency(target?.currencyCode ?: "")
            CurrencyFormatter.formatCurrency(value, currencyCode = target?.currencyCode ?: "", locale)
        }.map { it.value }
    }

    fun onDismissError() {
        handleError(false, ErrorState.None)
    }

    fun onAction(product: ProductItemState) {
        if (product.isBucketed) {
            val toRemove = this.cartProducts.firstOrNull { it.id == product.id }
            toRemove?.let { removeProduct(it) }
        } else {
            val toSave = ProductEntity(
                id = product.id,
                name = product.name,
                description = product.description,
                price = product.rawPrice,
                image = product.imageUrl,
                quantity = 1,
                stock = product.stock
            )
            storeProduct(toSave)
        }
    }


}