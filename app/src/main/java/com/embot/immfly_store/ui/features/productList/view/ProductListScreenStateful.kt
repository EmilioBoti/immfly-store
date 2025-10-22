package com.embot.immfly_store.ui.features.productList.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.embot.immfly_store.domain.models.uiState.ProductItemState
import com.embot.immfly_store.ui.components.productList.CardProduct
import com.embot.immfly_store.ui.theme.ReadColor


@Composable
fun ProductListScreenStateful(
    paddingValues: PaddingValues,
    products: List<ProductItemState>
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(ReadColor).padding(paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier.padding(5.dp)
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
fun ProductListScreenStatefulPreview() {
    ProductListScreenStateful(
        paddingValues = PaddingValues(20.dp),
        products = listOf(
            ProductItemState(
                id = "4",
                name = "Samsung Galaxy Watch6 Classic BT",
                description = "Este fantástico smartwatch de Samsung cae de precio en el outlet de MediaMarkt. La principal característica de este modelo es su dial giratorio, con el que podemos controlar la navegación del mismo. Permite responder mensajes, llamadas y pagar, además de monitorizar nuestra salud.",
                price = "$339.99",
                rawPrice = "$339.99",
                currencies = listOf(),
                stock = 8,
                imageUrl = "https://raw.githubusercontent.com/EmilioBoti/api-server/refs/heads/master/public/Samsung-Galaxy-watch6-classic-BT.png",
                isBucketed = false
            ),
            ProductItemState(
                id = "5",
                name = "Samsung Galaxy Watch6 Classic BT",
                description = "Este fantástico smartwatch de Samsung cae de precio en el outlet de MediaMarkt. La principal característica de este modelo es su dial giratorio, con el que podemos controlar la navegación del mismo. Permite responder mensajes, llamadas y pagar, además de monitorizar nuestra salud.",
                price = "$339.99",
                rawPrice = "$339.99",
                currencies = listOf(),
                stock = 8,
                imageUrl = "https://raw.githubusercontent.com/EmilioBoti/api-server/refs/heads/master/public/Samsung-Galaxy-watch6-classic-BT.png",
                isBucketed = false
            )
        )
    )
}