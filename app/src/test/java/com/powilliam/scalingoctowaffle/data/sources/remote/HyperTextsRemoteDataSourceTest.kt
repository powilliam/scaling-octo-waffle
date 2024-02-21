package com.powilliam.scalingoctowaffle.data.sources.remote

import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.junit.Test

class HyperTextsRemoteDataSourceTest {

    @Test
    fun itShouldBeAbleToGetDocumentByHref() = runBlocking {
        val dataSource = HyperTextsRemoteDataSourceImpl(Jsoup.connect("https://google.com"))
        val document = dataSource.byHref("https://google.com")

        assert(document.hasText())
    }
}