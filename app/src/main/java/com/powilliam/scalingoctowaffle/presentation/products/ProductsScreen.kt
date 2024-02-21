package com.powilliam.scalingoctowaffle.presentation.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.powilliam.scalingoctowaffle.data.entities.Pricing
import com.powilliam.scalingoctowaffle.data.entities.Product
import com.powilliam.scalingoctowaffle.data.entities.ProductWithPricings
import com.powilliam.scalingoctowaffle.data.entities.currency
import com.powilliam.scalingoctowaffle.data.entities.date

@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    products: List<ProductWithPricings> = emptyList(),
) {
    Scaffold { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        // TODO: Sections (Adicionado recentemente, Diminuiu de preço, aumentou de preço)
        LazyColumn(
            modifier = modifier
                .testTag("products-list")
                .fillMaxSize()
                .padding(top = topPadding)
        ) {
            items(products, { it.product.id }) { productWithPricing ->
                ProductWithPricingCard(data = productWithPricing)
            }
        }
    }
}

@Composable
fun ProductWithPricingCard(
    modifier: Modifier = Modifier,
    data: ProductWithPricings,
    onPressed: () -> Unit = {}
) {

    val pricing = data.pricings.maxByOrNull { it.createdAt }

    Surface(
        modifier = modifier
            .testTag("product-with-pricing-card")
            .fillMaxWidth(),
        onClick = onPressed,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = modifier.fillMaxWidth(0.8f)) {
                Text(
                    text = data.product.name,
                    style = MaterialTheme.typography.titleMedium
                )

                pricing?.let {
                    Text(
                        modifier = modifier.padding(top = 4.dp),
                        text = it.date(),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Column {
                pricing?.let {
                    Text(
                        text = it.currency(),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProductsScreenPreview() {
    val data = ProductWithPricings(
        product = Product.forTesting(name = "iPhone 14"),
        pricings = listOf(
            Pricing.forTesting(productId = 1)
        )
    )

    ProductWithPricingCard(data = data)
}