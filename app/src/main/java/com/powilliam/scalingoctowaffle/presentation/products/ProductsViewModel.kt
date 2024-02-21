package com.powilliam.scalingoctowaffle.presentation.products

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OverwritingInputMerger
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.powilliam.scalingoctowaffle.data.entities.Product
import com.powilliam.scalingoctowaffle.data.repositories.ProductsRepository
import com.powilliam.scalingoctowaffle.data.workers.ScrapProductPriceFromSellerWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val products: ProductsRepository
) : ViewModel() {
    val allWithPricings = products.allWithPricings()
        .shareIn(viewModelScope, started = SharingStarted.WhileSubscribed())

    fun onInsert(href: String, onSuccess: (Product) -> Unit) = viewModelScope.launch {
        try {
            products.insert(href)
            onSuccess(products.byHref(href))
        } catch (_: Exception) {}
    }
}