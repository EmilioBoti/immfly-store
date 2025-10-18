package com.embot.immfly_store.ui.features.productList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.embot.immfly_store.domain.models.apiModel.ApiProduct
import com.embot.immfly_store.ui.features.productList.repository.IProductListRepository
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

    private val _product: MutableStateFlow<ArrayList<ApiProduct>> = MutableStateFlow(arrayListOf())
    val product: StateFlow<ArrayList<ApiProduct>> = _product.onStart {
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
                .onSuccess {
                    _product.value = it as ArrayList<ApiProduct>
                }
                .onFailure {
                    print(it.message)
                }

        }
    }

}