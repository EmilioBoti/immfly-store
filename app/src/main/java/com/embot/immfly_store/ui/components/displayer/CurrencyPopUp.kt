package com.embot.immfly_store.ui.components.displayer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.embot.immfly_store.R
import com.embot.immfly_store.domain.models.constants.CurrencyType
import com.embot.immfly_store.ui.theme.PrimaryColor
import com.embot.immfly_store.ui.theme.spacing


@Composable
fun CurrencyPopUp(
    currentCurrency: CurrencyType,
    onDismiss: () -> Unit,
    onSelected: (currency: CurrencyType) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
        )
    ) {
        Card(
            shape = RoundedCornerShape(MaterialTheme.spacing.cardProductRadius)
        ) {
            LazyColumn(
                modifier = Modifier.widthIn(
                    min = MaterialTheme.spacing.currencyPopUpWidth,
                    max = MaterialTheme.spacing.currencyPopUpWidth
                ).background(Color.White)
                    .padding(MaterialTheme.spacing.extralNormal)
                ,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                items(
                    items = arrayOf(CurrencyType.EUR, CurrencyType.GBP, CurrencyType.USD),
                    key = { it.type }
                ) { currency ->
                    val isSelected = currency == currentCurrency
                    Column(
                        modifier = Modifier.clickable(
                            enabled = true,
                            onClick = { onSelected(currency) }
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.extralNormal),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(currency.currencyName),
                                style = TextStyle(
                                    color = if(isSelected) PrimaryColor else Color.Black,
                                    fontSize = MaterialTheme.spacing.textMedium,
                                    fontFamily = FontFamily(Font(R.font.notosans_semibold600))
                                ),
                            )
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Outlined.CheckCircle,
                                    contentDescription = null,
                                    tint = PrimaryColor
                                )
                            }
                        }
                        Box(modifier = Modifier.fillMaxWidth()
                            .height(0.3.dp)
                            .background(Color.LightGray)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun CurrencyPopUpPreview() {
    CurrencyPopUp(
        currentCurrency = CurrencyType.EUR,
        onDismiss = { }
    ) {
    }
}