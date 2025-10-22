package com.embot.immfly_store.ui.components.productList

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.embot.immfly_store.R
import com.embot.immfly_store.domain.models.uiState.ProductItemState
import com.embot.immfly_store.ui.theme.GreenColor
import com.embot.immfly_store.ui.theme.YellowColor
import com.embot.immfly_store.ui.theme.spacing


@Composable
fun CardProduct(
    modifier: Modifier = Modifier,
    product: ProductItemState,
    onAction: (product: ProductItemState) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(MaterialTheme.spacing.cardProductRadius)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(MaterialTheme.spacing.medium)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.spacing.imageHeight)
                    .clip(
                        shape = RoundedCornerShape(
                            corner = CornerSize(MaterialTheme.spacing.cardInnerRadius)
                        )
                    )
            ) {
                 AsyncImage(
                     modifier = Modifier.fillMaxSize(),
                     model = product.imageUrl,
                     contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.none, MaterialTheme.spacing.extralNormal),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.gapExtralSmall)
            ) {
                Text(
                    text = product.name,
                    style = TextStyle(
                        fontSize = MaterialTheme.spacing.textMediumExtra,
                        fontFamily = FontFamily(Font(R.font.notosans_bold700))
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${stringResource(R.string.stock)}: ",
                        style = TextStyle(
                            fontSize = MaterialTheme.spacing.textSmall,
                            fontFamily = FontFamily(Font(R.font.notosans_light300))
                        )
                    )
                    Text(
                        text = "${product.stock}",
                        style = TextStyle(
                            fontSize = MaterialTheme.spacing.textNormal,
                            fontFamily = FontFamily(Font(R.font.notosans_bold700))
                        )
                    )
                }
                Text(
                    text = product.description,
                    style = TextStyle(
                        fontSize = MaterialTheme.spacing.textNormal,
                        fontFamily = FontFamily(Font(R.font.notosans_light300))
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = product.price,
                        style = TextStyle(
                            fontSize = MaterialTheme.spacing.textLarge,
                            fontFamily = FontFamily(Font(R.font.notosans_bold700))
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    product.currencies.map { it }.forEach { it ->
                        Text(
                            text = "≈ $it",
                            style = TextStyle(
                                fontSize = MaterialTheme.spacing.textSmall,
                                fontFamily = FontFamily(Font(R.font.notosans_regular400)),
                                fontStyle = FontStyle.Italic
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Button (
                    onClick = {  onAction(product) },
                    shape = RoundedCornerShape(MaterialTheme.spacing.cartButtonRadius),
                    colors = ButtonColors(
                        containerColor = if(product.isBucketed) GreenColor else YellowColor,
                        contentColor = Color.White,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.gapExtralSmall)
                    ) {
                        Text(
                            text = if(product.isBucketed) stringResource(R.string.remove_from_cart) else stringResource(R.string.add_from_cart),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.notosans_semibold600))
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(Icons.Outlined.ShoppingCart, contentDescription = "")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun CardProductPreview() {
    CardProduct(
        product = ProductItemState(
            id = "4",
            name = "Samsung Galaxy Watch6 Classic BT",
            description = "Este fantástico smartwatch de Samsung cae de precio en el outlet de MediaMarkt. La principal característica de este modelo es su dial giratorio, con el que podemos controlar la navegación del mismo. Permite responder mensajes, llamadas y pagar, además de monitorizar nuestra salud.",
            price = "$339.99",
            rawPrice = "$339.99",
            currencies = listOf(),
            stock = 8,
            imageUrl = "https://raw.githubusercontent.com/EmilioBoti/api-server/refs/heads/master/public/Samsung-Galaxy-watch6-classic-BT.png",
            isBucketed = true
        )
    ) {

    }
}