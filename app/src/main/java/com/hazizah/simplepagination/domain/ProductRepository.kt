package com.hazizah.simplepagination.domain

interface ProductRepository {
    fun searchProduct(query: String): Listing<Product>
}