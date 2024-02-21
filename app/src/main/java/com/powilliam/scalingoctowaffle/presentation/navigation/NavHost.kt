package com.powilliam.scalingoctowaffle.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.powilliam.scalingoctowaffle.presentation.products.ProductsScreen

@Composable
fun ApplicationNavHost(
    windowSizeClass: WindowSizeClass,
    controller: NavHostController = rememberNavController()
) {
    NavHost(navController = controller, startDestination = Destination.Products.route) {
        composable(Destination.Products.route, Destination.Products.arguments) {
            ProductsScreen()
        }
    }
}