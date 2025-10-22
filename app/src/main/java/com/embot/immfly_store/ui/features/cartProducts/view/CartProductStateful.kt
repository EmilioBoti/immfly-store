package com.embot.immfly_store.ui.features.cartProducts.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun CartProductStateful(
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.Red).padding(paddingValues)
    ) {

    }
}