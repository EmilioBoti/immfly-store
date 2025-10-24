package com.embot.immfly_store.ui.features.cartProducts.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import com.embot.immfly_store.domain.models.uiState.ActionCartState
import com.embot.immfly_store.domain.models.uiState.CartItemState
import com.embot.immfly_store.domain.models.uiState.DisplayActionState
import com.embot.immfly_store.ui.components.cartProduct.CartProductItem
import com.embot.immfly_store.ui.components.displayer.InfoActionPopUp
import com.embot.immfly_store.ui.theme.ReadColor
import com.embot.immfly_store.ui.theme.YellowColor
import com.embot.immfly_store.ui.theme.spacing
import kotlin.String


@Composable
fun CartProductScreen(
    paddingValues: PaddingValues,
    cartItems: List<CartItemState>,
    totalPrice: String,
    actionState: DisplayActionState,
    decrease: (CartItemState) -> Unit,
    increase: (CartItemState) -> Unit,
    delete: (CartItemState) -> Unit,
    confirmDelete: (Boolean) -> Unit = {},
    pay: () -> Unit = {},
    navigateBack: () -> Unit = {}
) {
    Box {
        if (actionState.isOpen) {
            when (actionState.actionCartState) {
                is ActionCartState.CartError -> {
                    if (actionState.actionCartState.isError) {
                        InfoActionPopUp(
                            title = stringResource(R.string.title_error),
                            message = actionState.actionCartState.message,
                            confirmText = stringResource(R.string.accept),
                            cancelText = stringResource(R.string.cancel),
                            isSingleAction = true,
                            onConfirm = { confirmDelete(true) },
                            onDismiss = { confirmDelete(false) }
                        )
                    }
                }
                ActionCartState.ConfirmDelete -> {
                    InfoActionPopUp(
                        title = stringResource(R.string.title_delete),
                        message = stringResource(R.string.delete_item_confirmation),
                        confirmText = "confirm",
                        cancelText = "Cancel",
                        onConfirm = { confirmDelete(true) },
                        onDismiss = { confirmDelete(false) }
                    )
                }
                ActionCartState.DeleteSuccess -> {}
                null -> {}
                ActionCartState.PaySuccessful -> {
                    InfoActionPopUp(
                        title = stringResource(R.string.success),
                        message = stringResource(R.string.success_payment),
                        confirmText = "confirm",
                        isSucceed = true,
                        isSingleAction = true,
                        onConfirm = navigateBack,
                        onDismiss = { confirmDelete(false) }
                    )
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
                .background(ReadColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cartItems.isNotEmpty()) {
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
                            increase = increase,
                            delete = delete
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
                        text = stringResource(R.string.empty_cart),
                        style = TextStyle(
                            fontSize = MaterialTheme.spacing.textLarge,
                            fontFamily = FontFamily(Font(R.font.notosans_regular400))
                        )
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
                        text = stringResource(R.string.total_price),
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
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.LightGray,
                    ),
                    shape = RoundedCornerShape(
                        corner = CornerSize(MaterialTheme.spacing.mediumExtra)
                    ),
                    enabled = cartItems.isNotEmpty(),
                    onClick = pay
                ) {
                    Text(text = stringResource(R.string.buy_item))
                }
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
//            CartItemState(
//                id = "",
//                name = "",
//                price = "",
//                image = "",
//                realPrice = 399.45,
//                quantity = 1,
//                stock = 6,
//            )
        ),
        totalPrice = "399.45",
        actionState = DisplayActionState(isOpen = false, actionCartState = ActionCartState.ConfirmDelete),
        decrease = {},
        increase = {},
        delete = {},
        confirmDelete = {}

    )
}