package com.embot.immfly_store.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.embot.immfly_store.ui.features.productList.viewModel.ProductListViewModel

@Composable
fun NavigationScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = PokemonListRoute) {
        composable<PokemonListRoute> {
            val viewModel: ProductListViewModel = hiltViewModel()
//            ProductListScreen(modifier,
//                viewModel
//            )
        }
    }
}