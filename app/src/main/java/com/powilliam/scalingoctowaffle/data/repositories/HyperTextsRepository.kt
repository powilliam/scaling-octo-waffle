package com.powilliam.scalingoctowaffle.data.repositories

import com.powilliam.scalingoctowaffle.data.sources.remote.HyperTextsRemoteDataSource
import org.jsoup.nodes.Document
import javax.inject.Inject

interface HyperTextsRepository {
    suspend fun byHref(href: String): Document
}

class HyperTextsRepositoryImpl @Inject constructor(
    private val dataSource: HyperTextsRemoteDataSource
) : HyperTextsRepository {
    override suspend fun byHref(href: String): Document = dataSource.byHref(href)
}