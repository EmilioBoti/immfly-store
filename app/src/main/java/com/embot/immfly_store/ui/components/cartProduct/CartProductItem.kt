package com.embot.immfly_store.ui.components.cartProduct

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.embot.immfly_store.R
import com.embot.immfly_store.domain.models.uiState.CartItemState
import com.embot.immfly_store.ui.theme.PrimaryColor
import com.embot.immfly_store.ui.theme.ReadColor
import com.embot.immfly_store.ui.theme.spacing


@Composable
fun CartProductItem(
    modifier: Modifier = Modifier,
    product: CartItemState,
    decrease: (CartItemState) -> Unit,
    increase : (CartItemState) -> Unit,
    delete : (CartItemState) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .height(200.dp)
            .clip(
                shape = RoundedCornerShape(
                    corner = CornerSize(4.dp)
                )
            )
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .width(100.dp)
                .fillMaxHeight()
                .padding(4.dp)
                .clip(
                    shape = RoundedCornerShape(
                        corner = CornerSize(4.dp)
                    )
                )
                .weight(.7f)
//                .background(Color.Red)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = product.image,
                contentDescription = null,
                placeholder = painterResource(R.drawable.samsung_galaxy_watch6_classic_bt),
            )
        }
        Column(
            modifier = Modifier.fillMaxHeight()
                .padding(10.dp)
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                Text(
                    text = product.name,
                    style = TextStyle(
                        fontSize = MaterialTheme.spacing.textMediumExtra,
                        fontFamily = FontFamily(Font(R.font.notosans_light300)),
                        fontWeight = FontWeight.W500
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
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
                            fontFamily = FontFamily(Font(R.font.notosans_regular400))
                        )
                    )
                }
            }
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                Text(
                    text = product.price,
                    style = TextStyle(
                        fontSize = MaterialTheme.spacing.textLarge,
                        fontFamily = FontFamily(Font(R.font.notosans_semibold600)),
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                    ) {
                        IconButton(
                            modifier = Modifier
                                .clip(
                                    shape = RoundedCornerShape(
                                        corner = CornerSize(MaterialTheme.spacing.roundedCornerRadius)
                                    )
                                )
                                .background(ReadColor),
                            onClick = { decrease(product) }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Remove,
                                contentDescription = null,
                            )
                        }
                        Text(
                            text = "${product.quantity}",
                            style = TextStyle(
                                fontSize = MaterialTheme.spacing.textMediumExtra,
                                fontFamily = FontFamily(Font(R.font.notosans_light300)),
                                fontWeight = FontWeight.W500
                            ),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                        IconButton(
                            modifier = Modifier
                                .clip(
                                    shape = RoundedCornerShape(
                                        corner = CornerSize(MaterialTheme.spacing.roundedCornerRadius)
                                    )
                                )
                                .background(ReadColor),
                            onClick = { increase(product) }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = null,
                            )
                        }
                    }
                    IconButton(onClick = { delete(product) }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete item from your cart",
                            tint = PrimaryColor
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun CartProductItemPreview() {
    CartProductItem(
        product = CartItemState(
            id = "",
            name = "Samsung Galaxy Watch6 Classic BT",
            price = "$299.99",
            image = "",
            realPrice = 299.99,
            quantity = 1,
            stock = 2
        ),
        decrease = {},
        increase = {},
        delete = {}
    )
}