package com.embot.immfly_store.ui.features.productList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.embot.immfly_store.domain.models.appModel.ProductModel
import com.embot.immfly_store.domain.models.constants.CurrencyType
import com.embot.immfly_store.domain.models.uiState.ProductItemState
import com.embot.immfly_store.ui.features.productList.repository.IProductListRepository
import com.embot.immfly_store.ui.utils.ProductUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: IProductListRepository
): ViewModel() {

    private val _product: MutableStateFlow<List<ProductItemState>> = MutableStateFlow(listOf())
    val product: StateFlow<List<ProductItemState>> = _product.onStart {
        getAllProducts()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )


    fun getAllProducts() {
        viewModelScope.launch {
            val reponse = productRepository.getAllProducts()
            reponse
                .onSuccess { apiProducts ->
                    val products = ProductUtils.parseToAppProduct(apiProducts)
                    _product.update { parseToProdcutState(products) }
                }
                .onFailure {
                    print(it.message)
                }

        }
    }

    fun parseToProdcutState(products: List<ProductModel>): List<ProductItemState> {
        return products.map { prod ->
            ProductItemState(
                id = prod.id,
                name = prod.name,
                description = prod.description,
                price = prod.price,
                imageUrl = prod.imageUrl,
                stock = prod.stock,
                isBucketed = false,
                currencyType = CurrencyType.EUR
            )
        }
    }

}