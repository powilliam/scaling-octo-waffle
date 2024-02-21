package com.powilliam.scalingoctowaffle.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.powilliam.scalingoctowaffle.presentation.products.ProductsScreen
import com.powilliam.scalingoctowaffle.presentation.products.ProductsViewModel

@Composable
fun ApplicationNavHost(
    windowSizeClass: WindowSizeClass,
    controller: NavHostController = rememberNavController()
) {
    NavHost(navController = controller, startDestination = Destination.Products.route) {
        composable(Destination.Products.route, Destination.Products.arguments) { backStackEntry ->
            val productsViewModel = hiltViewModel<ProductsViewModel>(backStackEntry)

            val products by productsViewModel.allWithPricings.collectAsState(initial = emptyList())

            ProductsScreen(products = products)
        }
    }
}