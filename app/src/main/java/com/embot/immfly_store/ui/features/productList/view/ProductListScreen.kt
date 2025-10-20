package com.embot.immfly_store.ui.features.productList.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.embot.immfly_store.ui.components.productList.CardProduct
import com.embot.immfly_store.ui.features.productList.viewModel.ProductListViewModel
import com.embot.immfly_store.ui.theme.ReadColor


@Composable
fun ProductListScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel
) {
    val products by viewModel.product.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.background(ReadColor).padding(paddingValues)
    ) {
        LazyColumn(
            modifier = modifier.padding(5.dp)
        ) {
            items(
                items = products,
                key = { it.id }
            ) { product ->
                CardProduct(
                    modifier = Modifier.padding(2.dp),
                    product = product
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {

}