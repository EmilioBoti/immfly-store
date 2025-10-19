package com.embot.immfly_store.ui.features.productList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.embot.immfly_store.domain.models.appModel.ProductModel
import com.embot.immfly_store.ui.features.productList.repository.IProductListRepository
import com.embot.immfly_store.ui.utils.ProductUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: IProductListRepository
): ViewModel() {

    private val _product: MutableStateFlow<List<ProductModel>> = MutableStateFlow(arrayListOf())
    val product: StateFlow<List<ProductModel>> = _product.onStart {
        getAllProducts()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = arrayListOf()
    )


    fun getAllProducts() {
        viewModelScope.launch {
            val reponse = productRepository.getAllProducts()
            reponse
                .onSuccess { apiProducts ->
                    _product.value = ProductUtils.parseToAppProduct(apiProducts)
                }
                .onFailure {
                    print(it.message)
                }

        }
    }

}