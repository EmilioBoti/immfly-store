package com.embot.immfly_store.ui.features.cartProducts.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.embot.immfly_store.ui.components.cartProduct.CartProductItem
import com.embot.immfly_store.ui.features.cartProducts.viewModel.CartProductViewModel
import com.embot.immfly_store.ui.theme.ReadColor
import com.embot.immfly_store.ui.theme.spacing


@Composable
fun CartProductStateful(
    paddingValues: PaddingValues,
    viewModel: CartProductViewModel
) {

    val products by viewModel.products.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
            .background(ReadColor).padding(paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier.padding(MaterialTheme.spacing.extraSmall)
        ) {
            items(
                items = products,
                key = { it.id }
            ) { cartItmes ->
                CartProductItem(
                    modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                    product = cartItmes,
                    decrease = { Log.i("decrease", "decrease") },
                    increase = { Log.i("increase", "increase") }
                )
            }
        }
    }
}