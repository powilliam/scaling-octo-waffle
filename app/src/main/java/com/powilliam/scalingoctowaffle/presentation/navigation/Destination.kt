package com.powilliam.scalingoctowaffle.presentation.navigation

import androidx.navigation.NamedNavArgument

sealed class Destination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Products : Destination("products")
}
