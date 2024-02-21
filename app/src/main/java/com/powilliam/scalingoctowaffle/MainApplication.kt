package com.powilliam.scalingoctowaffle

import android.app.Application
import androidx.work.Configuration
import com.powilliam.scalingoctowaffle.di.ScallingOctoWaffleWorkerConfiguration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {
    @ScallingOctoWaffleWorkerConfiguration
    @Inject
    lateinit var configuration: Configuration

    override val workManagerConfiguration: Configuration
        get() = configuration
}