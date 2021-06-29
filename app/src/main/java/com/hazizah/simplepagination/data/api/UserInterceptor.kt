package com.hazizah.simplepagination.data.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class UserInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer hmc6DOHzMljgYv4VStgkqLL8eChnL6xtN-xHQb2oj45_JQ")
            .build()
        return chain.proceed(newRequest)
    }
}