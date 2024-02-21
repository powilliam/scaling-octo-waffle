package com.powilliam.scalingoctowaffle.data.repositories

import com.powilliam.scalingoctowaffle.data.sources.remote.HyperTextsRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.jsoup.nodes.Document
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class HyperTextsRepositoryTest {

    @Test
    fun itShouldBeAbleToGetDocumentByHref() = runBlocking {
        val dataSource = mock<HyperTextsRemoteDataSource> {
            onBlocking { byHref("https://google.com") } doReturn Document("https://google.com")
        }
        val repository = HyperTextsRepositoryImpl(dataSource)

        val document = repository.byHref("https://google.com")

        assert(document.baseUri() == "https://google.com")
    }
}