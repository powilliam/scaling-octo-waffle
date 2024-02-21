package com.powilliam.scalingoctowaffle.presentation.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.powilliam.scalingoctowaffle.presentation.theming.ApplicationTheme

private val modifier = Modifier

@Composable
fun ProductsScreen() {
    Scaffold { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            Text(text = "Hello World, ProductsScreen!")
        }
    }
}

@Preview
@Composable
private fun ProductsScreenPreview() {
    val context = LocalContext.current

    ApplicationTheme({ context }) {
        ProductsScreen()
    }
}