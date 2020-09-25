package com.hazizah.simplepagination.data.repository

import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.hazizah.simplepagination.data.api.GithubService
import com.hazizah.simplepagination.domain.Listing
import com.hazizah.simplepagination.domain.User
import com.hazizah.simplepagination.domain.UserRepository
import java.util.concurrent.Executor

class UserRepositoryImpl(
    private val service: GithubService,
    private val retryExecutor: Executor
) : UserRepository {

    override fun searchUsers(query: String): Listing<User> {
        val sourceFactory = UserDataSourceFactory(
            service,
            retryExecutor,
            query
        )
        val pagedList = sourceFactory.toLiveData(
            pageSize = 25
        )
        return Listing(
            pagedList = pagedList,
            networkState = Transformations.switchMap(
                sourceFactory.sourceLiveData,
                UserDataSource::state
            ),
            retry = { sourceFactory.sourceLiveData.value?.retryAllFailed() },
            totalCount = Transformations.switchMap(
                sourceFactory.sourceLiveData,
                UserDataSource::totalCount
            )
        )
    }

}