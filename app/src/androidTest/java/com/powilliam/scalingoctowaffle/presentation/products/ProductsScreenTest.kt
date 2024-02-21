package com.powilliam.scalingoctowaffle.presentation.products

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
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
        rule.setContent {
            ApplicationTheme({ rule.activity }) {
                ProductsScreen()
            }
        }

        rule.onNodeWithText("Hello World, ProductsScreen!")
            .assertExists()
    }
}