package com.hazizah.simplepagination.domain

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: String,
    @SerializedName("name") val productName: String,
    @SerializedName("price") val productPrice: String,
    @SerializedName("images") val images: Images
)

data class Images(
    @SerializedName("large_urls") val largeUrl: List<String>,
    @SerializedName("small_urls") val smallUrl: List<String>
)
