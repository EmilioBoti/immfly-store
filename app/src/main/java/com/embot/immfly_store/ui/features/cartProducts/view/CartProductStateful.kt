package com.embot.immfly_store.ui.features.cartProducts.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.embot.immfly_store.ui.features.cartProducts.viewModel.CartProductViewModel


@Composable
fun CartProductStateful(
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: CartProductViewModel
) {

    val cartState by viewModel.products.collectAsStateWithLifecycle()
    val actionState by viewModel.actionState.collectAsStateWithLifecycle()

    CartProductScreen(
        paddingValues = paddingValues,
        cartItems = cartState.products,
        totalPrice = cartState.totalPrice,
        actionState = actionState,
        decrease = { cartItem -> viewModel.decreaseItemCuantity(cartItem) },
        increase = { cartItem -> viewModel.increaseItemCuantity(cartItem) },
        delete = { cartItem -> viewModel.deleteItem(cartItem) },
        confirmDelete = { isConfirmed -> viewModel.confirmDelete(isConfirmed) },
        pay = { viewModel.payCartOrder() },
        navigateBack = { navController.popBackStack() }
    )

}