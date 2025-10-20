package com.embot.immfly_store

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.embot.immfly_store.ui.components.bottomNavigation.BottomNavBarView
import com.embot.immfly_store.ui.features.productList.view.ProductListScreen
import com.embot.immfly_store.ui.features.productList.viewModel.ProductListViewModel
import com.embot.immfly_store.ui.navigation.PokemonListRoute
import com.embot.immfly_store.ui.theme.ImmflystoreTheme
import com.embot.immfly_store.ui.theme.PrimaryColor
import com.embot.immfly_store.ui.utils.NavUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImmflystoreTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(text = "Products")
                            },
                            actions = {
                                IconButton(onClick = {
                                    Log.i("CurrencyExchange", "Currency Exchange clicked")
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.CurrencyExchange,
                                        contentDescription = "Currency exchange"
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = PrimaryColor,
                                titleContentColor = Color.White,
                                actionIconContentColor = Color.White
                            )
                        )
                    },
                    bottomBar = {
                        var itemSelected by remember { mutableIntStateOf(0) }
                        BottomAppBar {
                            BottomNavBarView(
                                items = NavUtils.navItem,
                                itemSelected = itemSelected,
                                onItemSelected = { index ->
                                    itemSelected = index
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = PokemonListRoute) {
                        composable<PokemonListRoute> {
                            val viewModel: ProductListViewModel = hiltViewModel()
                            ProductListScreen(
                                paddingValues = innerPadding,
                                modifier = Modifier.fillMaxSize(),
                                viewModel = viewModel
                            )
                        }
                    }

                }
            }
        }
    }
}