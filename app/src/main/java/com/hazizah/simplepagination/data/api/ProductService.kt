package com.hazizah.simplepagination.data.api

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.hazizah.simplepagination.domain.Product
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("products")
    suspend fun getProducts(
        @Query("keywords") keyword: String,
        @Query("price_range") price: String? = "0:0",
        @Query("premium_seller") premiumSeller: Boolean = true,
        @Query("condition") condition: String?,
        @Query("offset") offset: Int? = 0,
        @Query("limit") pageSize: Int? = 20
    ): Response<ListingData>

    class ListingData(
        @SerializedName("data")
        val children: List<Product>
    )

    companion object {
        private const val API_URL = "https://api.bukalapak.com/"

        fun create(): ProductService {
            val logger = HttpLoggingInterceptor { Log.d("API", it) }
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val auth = UserInterceptor()

            val client = OkHttpClient.Builder()
                .addInterceptor(auth)
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(HttpUrl.parse(API_URL)!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductService::class.java)
        }
    }
}
