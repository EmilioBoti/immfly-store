package com.embot.immfly_store.ui.components.bottomNavigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.embot.immfly_store.ui.navigation.NavItem
import com.embot.immfly_store.ui.utils.NavUtils


@Composable
fun BottomNavBarView(
    items: Array<NavItem>,
    itemSelected: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
    ) {
        items.forEachIndexed { index, navItem ->
            val isSelected = itemSelected == index
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label,
                    )
                },
                label = {
                    Text(
                        text = navItem.label,
                    )
                }
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun BottomNavBarViewPreview() {
    BottomNavBarView(
        items = NavUtils.navItem,
        0
    ) {

    }
}