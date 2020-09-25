package com.hazizah.simplepagination.domain

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("login") val account: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("url") val userUrl: String,
    @SerializedName("html_url") val githubUrl: String
)