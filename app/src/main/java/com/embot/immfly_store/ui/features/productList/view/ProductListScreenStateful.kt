package com.embot.immfly_store.ui.features.productList.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.embot.immfly_store.R
import com.embot.immfly_store.domain.models.uiState.ErrorState
import com.embot.immfly_store.domain.models.uiState.GenericState
import com.embot.immfly_store.domain.models.uiState.ProductItemState
import com.embot.immfly_store.ui.components.displayer.InfoActionPopUp
import com.embot.immfly_store.ui.components.productList.CardProduct
import com.embot.immfly_store.ui.theme.ReadColor
import com.embot.immfly_store.ui.theme.spacing


@Composable
fun ProductListScreenStateful(
    paddingValues: PaddingValues,
    error: GenericState,
    products: List<ProductItemState>,
    onAction: (product: ProductItemState) -> Unit,
    onDismissPopup: () -> Unit
) {

    if (error.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF1F1F1)), // your background
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp,
                modifier = Modifier.size(48.dp)
            )
        }
    }
    if (error.isError) {
        val message = when(error.error) {
            ErrorState.NetworkError -> "Check your internet connection."
            ErrorState.None -> ""
            ErrorState.UnknownError -> "Unexpected error occurred."
        }

        InfoActionPopUp(
            title = stringResource(R.string.title_error),
            message = message,
            confirmText = stringResource(R.string.accept),
            onConfirm = onDismissPopup,
            onDismiss = onDismissPopup
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(ReadColor).padding(paddingValues)
    ) {
        if (products.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(5.dp)
            ) {
                items(
                    items = products,
                    key = { it.id }
                ) { product ->
                    CardProduct(
                        modifier = Modifier.padding(2.dp),
                        product = product,
                        onAction =  onAction
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(MaterialTheme.spacing.emptyListIconHeight),
                    imageVector = Icons.Default.Inbox,
                    contentDescription = stringResource(R.string.empty_cart_description),
                    tint = Color(0xFF616161),
                )
                Text(
                    text = stringResource(R.string.empty_product_list),
                    style = TextStyle(
                        fontSize = MaterialTheme.spacing.textLarge,
                        fontFamily = FontFamily(Font(R.font.notosans_regular400))
                    )
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
        error = GenericState(
            isError = false,
            error = ErrorState.NetworkError
        ),
        products = listOf(
            ProductItemState(
                id = "4",
                name = "Samsung Galaxy Watch6 Classic BT",
                description = "Este fantástico smartwatch de Samsung cae de precio en el outlet de MediaMarkt. La principal característica de este modelo es su dial giratorio, con el que podemos controlar la navegación del mismo. Permite responder mensajes, llamadas y pagar, además de monitorizar nuestra salud.",
                price = "$339.99",
                rawPrice = 339.99,
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
                rawPrice = 339.99,
                currencies = listOf(),
                stock = 8,
                imageUrl = "https://raw.githubusercontent.com/EmilioBoti/api-server/refs/heads/master/public/Samsung-Galaxy-watch6-classic-BT.png",
                isBucketed = false
            )
        ),
        onAction = {},
        onDismissPopup = {}
    )
}