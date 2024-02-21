package com.powilliam.scalingoctowaffle.data.sources.remote

import com.powilliam.scalingoctowaffle.di.HyperTextsClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Connection
import org.jsoup.helper.HttpConnection
import org.jsoup.nodes.Document
import javax.inject.Inject

interface HyperTextsRemoteDataSource {
    suspend fun byHref(href: String): Document
}

class HyperTextsRemoteDataSourceImpl @Inject constructor(
    @HyperTextsClient private val client: Connection
) : HyperTextsRemoteDataSource {
    override suspend fun byHref(href: String): Document = withContext(Dispatchers.IO) {
        client.newRequest(href).get()
    }
}