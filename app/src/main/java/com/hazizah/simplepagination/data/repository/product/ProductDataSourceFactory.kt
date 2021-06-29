package com.hazizah.simplepagination.data.repository.product

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.hazizah.simplepagination.data.api.ProductService
import com.hazizah.simplepagination.domain.Product
import java.util.concurrent.Executor

class ProductDataSourceFactory(
    private val service: ProductService,
    private val retryExecutor: Executor,
    private val query: String
) : DataSource.Factory<Int, Product>() {
    val sourceLiveData = MutableLiveData<ProductDataSource>()

    override fun create(): DataSource<Int, Product> {
        val productDataSource = ProductDataSource(service, retryExecutor, query)
        sourceLiveData.postValue(productDataSource)
        return productDataSource
    }
}