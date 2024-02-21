package com.powilliam.scalingoctowaffle.presentation.products

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.powilliam.scalingoctowaffle.data.entities.Product
import com.powilliam.scalingoctowaffle.data.entities.ProductWithPricings
import com.powilliam.scalingoctowaffle.presentation.theming.ApplicationTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductsScreenTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun itShouldBeAbleToRenderCorrectly() {
        val products = listOf(
            ProductWithPricings(
                product = Product.forTesting(id = 1, name = "iPhone 13"),
                pricings = listOf()
            ),
            ProductWithPricings(
                product = Product.forTesting(id = 2, name = "iPhone 14"),
                pricings = listOf()
            ),
            ProductWithPricings(
                product = Product.forTesting(id = 3, name = "iPhone 15"),
                pricings = listOf()
            )
        )

        rule.setContent {
            ApplicationTheme({ rule.activity }) {
                ProductsScreen(products = products)
            }
        }

        rule.onNodeWithTag("products-list")
            .assertExists()

        products.forEach {
            rule.onNodeWithText(it.product.name).assertExists()
        }
    }
}