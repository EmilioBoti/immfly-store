package com.embot.immfly_store.ui.features.cartProducts.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.embot.immfly_store.R
import com.embot.immfly_store.domain.models.uiState.CartItemState
import com.embot.immfly_store.ui.components.cartProduct.CartProductItem
import com.embot.immfly_store.ui.theme.ReadColor
import com.embot.immfly_store.ui.theme.YellowColor
import com.embot.immfly_store.ui.theme.spacing
import kotlin.String


@Composable
fun CartProductScreen(
    paddingValues: PaddingValues,
    cartItems: List<CartItemState>,
    totalPrice: String,
    decrease: (CartItemState) -> Unit,
    increase : (CartItemState) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
            .background(ReadColor)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(MaterialTheme.spacing.extraSmall)
        ) {
            items(
                items = cartItems,
                key = { it.id }
            ) { cartItmes ->
                CartProductItem(
                    modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                    product = cartItmes,
                    decrease = decrease,
                    increase = increase
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(MaterialTheme.spacing.extralNormal),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total price",
                    style = TextStyle(
                        fontSize = MaterialTheme.spacing.textMediumExtra,
                        fontFamily = FontFamily(Font(R.font.notosans_semibold600))
                    )
                )
                Text(
                    text = totalPrice,
                    style = TextStyle(
                        fontSize = MaterialTheme.spacing.textLarge,
                        fontFamily = FontFamily(Font(R.font.notosans_semibold600))
                    )
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium),
                colors = ButtonColors(
                    containerColor = YellowColor,
                    contentColor = Color.White,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.White,
                ),
                shape = RoundedCornerShape(
                    corner = CornerSize(20.dp)
                ),
                onClick = { }
            ) {
                Text(text = "Buy")
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun CartProductScreenPreview() {
    CartProductScreen(
        paddingValues = PaddingValues(0.dp),
        cartItems = listOf(
            CartItemState(
                id = "",
                name = "",
                price = "",
                image = "",
                realPrice = 399.45,
                quantity = 1,
                stock = 6,
            )
        ),
        totalPrice = "399.45",
        decrease = {},
        increase = {}
    )
}