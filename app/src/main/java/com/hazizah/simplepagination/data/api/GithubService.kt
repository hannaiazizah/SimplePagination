package com.hazizah.simplepagination.data.api

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.hazizah.simplepagination.domain.User
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/users")
    suspend fun getUsers(
        @Query("q") q: String,
        @Query("page") page: Int? = 0,
        @Query("per_page") pageSize: Int? = 20
    ): Response<ListingData>

    class ListingData(
        @SerializedName("items")
        val children: List<User>,
        @SerializedName("total_count")
        val totalCount: Int,
        @SerializedName("incomplete_results")
        val isComplete: Boolean
    )

    companion object {
        private const val API_URL = "https://api.github.com/"

        fun create(): GithubService {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("API", it) })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(HttpUrl.parse(API_URL)!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService::class.java)
        }
    }
}