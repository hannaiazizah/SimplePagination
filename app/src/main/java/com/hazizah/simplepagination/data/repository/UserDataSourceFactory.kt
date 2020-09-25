package com.hazizah.simplepagination.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.hazizah.simplepagination.data.api.GithubService
import com.hazizah.simplepagination.domain.User
import java.util.concurrent.Executor

class UserDataSourceFactory(
    private val service: GithubService,
    private val retryExecutor: Executor,
    private val query: String
) : DataSource.Factory<Int, User>() {
    val sourceLiveData = MutableLiveData<UserDataSource>()

    override fun create(): DataSource<Int, User> {
        val userDataSource = UserDataSource(service, retryExecutor, query)
        sourceLiveData.postValue(userDataSource)
        return userDataSource
    }
}