package com.hazizah.simplepagination.domain

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val productName: String,
    @SerializedName("price") val productPrice: String,
    @SerializedName("small_urls") val thumbnailUrl: String
)