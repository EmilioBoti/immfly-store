package com.embot.immfly_store.ui.features.cartProducts.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.embot.immfly_store.ui.features.cartProducts.viewModel.CartProductViewModel


@Composable
fun CartProductStateful(
    paddingValues: PaddingValues,
    viewModel: CartProductViewModel
) {

    val cartState by viewModel.products.collectAsStateWithLifecycle()

    CartProductScreen(
        paddingValues = paddingValues,
        cartItems = cartState.products,
        totalPrice = cartState.totalPrice,
        decrease = { cartItem -> viewModel.decreaseItemCuantity(cartItem) },
        increase = { cartItem -> viewModel.increaseItemCuantity(cartItem) }
    )

}