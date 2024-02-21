package com.powilliam.scalingoctowaffle.data.workers

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.workDataOf
import com.powilliam.scalingoctowaffle.TestScrapProductPriceFromSellerWorkerFactory
import com.powilliam.scalingoctowaffle.data.entities.Product
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ScrapProductPriceFromSellerWorkerTest {

    @Test
    fun itShouldNotBeAbleToScrapProductWhenSellerDoesNotExist() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val product = Product.forTesting(
            name = "Not Found Product",
            href = "https://ecommerce.not.found/product/1"
        )
        val data = workDataOf("productId" to product.id, "href" to product.href)

        val worker = TestListenableWorkerBuilder<ScrapProductPriceFromSellerWorker>(context, inputData = data)
            .setWorkerFactory(TestScrapProductPriceFromSellerWorkerFactory())
            .build()

        val result = worker.doWork()

        assert(result is ListenableWorker.Result.Failure)
    }


    @Test
    fun itShouldBeAbleToScrapProductPriceFromAmazon() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val product = Product.forTesting(
            name = "Echo Dot 5",
            href = "https://www.amazon.com.br/Echo-Dot-5ª-geração-Cor-Preta/dp/B09B8VGCR8/ref=sr_1_1?crid=26PDQ0HGZ8PX5&dib=eyJ2IjoiMSJ9.14cZBgy_n6hLGjrn9NIG4qVdiBu_XPObJ3QfIlBnSg4imvMSD3KR5u9mRwagfitp3LrGD1B7ZqOihioZZHb0LEb3UUygrgnk8vQggLdUzn0eFGZiPezZ76FO2PLvKFqy4zUiXNWlRTFIG8dxcFd40kpbpJjSCUarRkt0eQ-F0jIg1hhkjZa5i9t6ytALvA6RJFJnwZDOFSkwlKnsAfDHktK1N4wgtLPxV-wOWuEPFz4ydVWKBFlQByhewoibtLB1M_jPZx7HgAO7pLWF-mmwOk6Ob94LpzvgA-xl_D5EMCU.z7P0vzeGGIRho99rK-2H8xWlTrnaeS51yOyZ27h9mGI&dib_tag=se&keywords=alexa&qid=1708390003&sprefix=ale%2Caps%2C299&sr=8-1&ufe=app_do%3Aamzn1.fos.95de73c3-5dda-43a7-bd1f-63af03b14751"
        )
        val data = workDataOf("productId" to product.id, "href" to product.href)

        val worker = TestListenableWorkerBuilder<ScrapProductPriceFromSellerWorker>(context, inputData = data)
            .setWorkerFactory(TestScrapProductPriceFromSellerWorkerFactory())
            .build()

        val result = worker.doWork()

        assert(result.outputData.getInt("productId", -1) == product.id)
        assert(result.outputData.getDouble("price", -1.0) != -1.0)
    }

    @Test
    fun itShouldBeAbleToScrapProductPriceFromKabum() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val product = Product.forTesting(
            name = "Prime Ninja",
            href = "https://www.kabum.com.br/produto/1/prime-ninja-kabum-"
        )
        val data = workDataOf("productId" to product.id, "href" to product.href)

        val worker = TestListenableWorkerBuilder<ScrapProductPriceFromSellerWorker>(context, inputData = data)
            .setWorkerFactory(TestScrapProductPriceFromSellerWorkerFactory())
            .build()

        val result = worker.doWork()

        assert(result.outputData.getInt("productId", -1) == product.id)
        assert(result.outputData.getDouble("price", -1.0) != -1.0)
    }
}