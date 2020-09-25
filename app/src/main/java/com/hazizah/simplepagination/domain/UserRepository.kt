package com.hazizah.simplepagination.domain

interface UserRepository {
    fun searchUsers(query: String): Listing<User>
}