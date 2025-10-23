package com.embot.immfly_store.ui.features.cartProducts.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.embot.immfly_store.domain.models.constants.CurrencyType
import com.embot.immfly_store.domain.models.roomEntity.ProductEntity
import com.embot.immfly_store.domain.models.uiState.ActionCartState
import com.embot.immfly_store.domain.models.uiState.CartItemState
import com.embot.immfly_store.domain.models.uiState.CartState
import com.embot.immfly_store.domain.models.uiState.DisplayActionState
import com.embot.immfly_store.domain.service.localResource.preference.IAppDataStore
import com.embot.immfly_store.domain.useCase.ConvertCurrencyUseCase
import com.embot.immfly_store.domain.useCase.Price
import com.embot.immfly_store.ui.features.cartProducts.repository.ICartProductRepository
import com.embot.immfly_store.ui.utils.CurrencyFormatter
import com.embot.immfly_store.ui.utils.ProductUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private var updateJob: Job? = null
    private var currency: CurrencyType = CurrencyType.EUR
    private var itemToDeleteId: String? = null

    private val _actionState: MutableStateFlow<DisplayActionState> = MutableStateFlow(DisplayActionState(isOpen = false, actionCartState = null))
    val actionState: StateFlow<DisplayActionState> = _actionState.asStateFlow()

    private val _products: MutableStateFlow<CartState> = MutableStateFlow(CartState("0.0", 0.0,listOf()))
    val products: StateFlow<CartState> = _products.onStart {
        loadData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CartState("0.0",0.0,listOf())
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
                    val total = cartItems.sumOf { it.price * it.quantity }
                    val price = currencyUseCase.getSelectedCurrencyPrice(total.toString(), currency.type)
                    _products.update {
                        CartState(
                            totalPrice = CurrencyFormatter.formatCurrencyPrice(price),
                            amount = price.price.toDouble(),
                            products = parseToCartItemState(cartItems)
                        )
                    }
                }
                products.onFailure {
                    _actionState.update {
                        it.copy(
                            isOpen = true,
                            actionCartState = ActionCartState.CartError(
                                isError = true,
                                message = "Failed to load cart items"
                            )
                        )
                    }
                }

            } catch (e: Exception) {
                _actionState.update {
                    it.copy(
                        isOpen = true,
                        actionCartState = ActionCartState.CartError(
                            isError = true,
                            message = "Something went wrong, Try again later"
                        )
                    )
                }
            }
        }
    }

    /**
     * debounce to detect when user is clicking too fast
     */
    fun onQuantityChanged(id: String, quantity: Int) {
        // cancel any pending update
        updateJob?.cancel()
        // start new debounce timer
        updateJob = viewModelScope.launch {
            delay(500L)
            cartRepository.updateProductQuantity(id, quantity)
        }
    }

    private fun removeProductFromCart(id: String) {
        viewModelScope.launch {
            val res = cartRepository.deleteFromCart(id)
            res.onSuccess {
                _actionState.update {
                    it.copy(
                        isOpen = true,
                        actionCartState = ActionCartState.DeleteSuccess
                    )
                }
            }
            res.onFailure {
                _actionState.update {
                    it.copy(
                        isOpen = false,
                        actionCartState =  ActionCartState.CartError(
                            isError = true,
                            message = "Something went wrong trying to remove item from cart"
                        )
                    )
                }
            }
        }
    }

    private fun updateTotalPrice() {
        _products.update { currentState ->
            val totla = calculateTotalPrice(currentState)
            currentState.copy(
                totalPrice = CurrencyFormatter.formatCurrencyPrice(calculateTotalPrice(currentState)),
                amount = totla.price.toDouble()
            )
        }
    }

    private fun calculateTotalPrice(cartState: CartState): Price {
        val total = cartState.products.sumOf { it.realPrice * it.quantity }
        return currencyUseCase.getSelectedCurrencyPrice("$total", currency.type)
    }

    private fun parseToCartItemState(cartItems: List<ProductEntity>): List<CartItemState> {
        return cartItems.map {
            CartItemState(
                id = it.id,
                name = it.name,
                price = CurrencyFormatter.formatCurrencyPrice(currencyUseCase.getSelectedCurrencyPrice("${it.price}", currency.type)),
                image = it.image,
                realPrice = it.price,
                quantity = it.quantity,
                stock = it.stock,
            )
        }
    }

    private fun updateQuantity(isUp: Boolean, itemId: String) {
        _products.update { currentState ->
            currentState.copy(
                products = currentState.products.map { item ->
                    if (item.id == itemId) {
                        val value = if (isUp) item.quantity + 1 else item.quantity - 1
                        if(value > item.stock || value < 1) return@map item
                        onQuantityChanged(itemId, value)
                        item.copy(quantity = value)
                    } else {
                        item
                    }
                }
            )
        }
    }

    fun increaseItemCuantity(cartItem: CartItemState) {
        updateQuantity(true, cartItem.id)
        updateTotalPrice()
    }

    fun decreaseItemCuantity(cartItem: CartItemState) {
        updateQuantity(false, cartItem.id)
        updateTotalPrice()

    }

    fun deleteItem(cartItem: CartItemState) {
        itemToDeleteId = cartItem.id
        _actionState.update {
            it.copy(
                isOpen = true,
                actionCartState = ActionCartState.ConfirmDelete
            )
        }
    }

    fun confirmDelete(confirmed: Boolean) {
        _actionState.update { it.copy(isOpen = false) }
        if (confirmed) {
            itemToDeleteId?.let {
                _products.update { currentState ->
                    currentState.copy(
                        products = currentState.products.filter { items -> items.id != it }
                    )
                }
                removeProductFromCart(it)
                updateTotalPrice()
            }
        }

    }

}