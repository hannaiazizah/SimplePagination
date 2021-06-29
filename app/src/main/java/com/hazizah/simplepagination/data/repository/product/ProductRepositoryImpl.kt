package com.hazizah.simplepagination.data.repository.product

import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.hazizah.simplepagination.data.api.ProductService
import com.hazizah.simplepagination.domain.Listing
import com.hazizah.simplepagination.domain.Product
import com.hazizah.simplepagination.domain.ProductRepository
import java.util.concurrent.Executor

class ProductRepositoryImpl(
    private val service: ProductService,
    private val retryExecutor: Executor
): ProductRepository {
    override fun searchProduct(query: String): Listing<Product> {
        val sourceFactory = ProductDataSourceFactory(
            service,
            retryExecutor,
            query
        )
        val pagedList = sourceFactory.toLiveData(
            pageSize = 25
        )
        return Listing(
            pagedList = pagedList,
            networkState = Transformations.switchMap(
                sourceFactory.sourceLiveData,
                ProductDataSource::state
            ),
            retry = { sourceFactory.sourceLiveData.value?.retryAllFailed() },
            totalCount = Transformations.switchMap(
                sourceFactory.sourceLiveData,
                ProductDataSource::totalCount
            )
        )
    }
}