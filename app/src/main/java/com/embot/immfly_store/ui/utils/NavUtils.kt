package com.embot.immfly_store.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import com.embot.immfly_store.ui.navigation.NavItem

object NavUtils {

    val navItem: Array<NavItem> = arrayOf(
        NavItem(
            label = "Explore",
            icon = Icons.Default.Search,
            route = ""
        ),
        NavItem(
            label = "Cart",
            icon = Icons.Default.ShoppingCart,
            route = ""
        )
    )

}