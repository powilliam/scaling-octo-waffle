package com.powilliam.scalingoctowaffle.data.sources.remote

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SellersRemoteDataSourceTest {

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        FirebaseApp.initializeApp(context)
    }

    @Test
    fun itShouldBeAbleToFindAmazon() = runTest {
        val dataSource = SellersRemoteDataSourceImpl(Firebase.database)
        val seller = dataSource.byHost("www.amazon.com.br")
        assert(seller?.host == "www.amazon.com.br")
    }

    @Test
    fun itShouldBeAbleToFindKabum() = runTest {
        val dataSource = SellersRemoteDataSourceImpl(Firebase.database)
        val seller = dataSource.byHost("www.kabum.com.br")
        assert(seller?.host == "www.kabum.com.br")
    }
}