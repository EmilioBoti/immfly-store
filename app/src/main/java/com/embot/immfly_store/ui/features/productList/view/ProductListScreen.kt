package com.embot.immfly_store.ui.features.productList.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.embot.immfly_store.ui.features.productList.viewModel.ProductListViewModel


@Composable
fun ProductListScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel
) {
    val products = viewModel.product.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.background(Color.Gray)
    ) {

    }

}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {

}