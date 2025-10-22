package com.embot.immfly_store.ui.features.productList.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.embot.immfly_store.ui.features.productList.viewModel.ProductListViewModel


@Composable
fun ProductListScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel
) {
    val products by viewModel.product.collectAsStateWithLifecycle()

    ProductListScreenStateful(
        paddingValues = paddingValues,
        products
    )
}
