package com.hazizah.simplepagination.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.hazizah.simplepagination.data.api.GithubService
import com.hazizah.simplepagination.domain.State
import com.hazizah.simplepagination.domain.User
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executor

class UserDataSource(
    private val service: GithubService,
    private val retryExecutor: Executor,
    private val query: String
) : PageKeyedDataSource<Int, User>() {

    val state = MutableLiveData<State>()
    val totalCount = MutableLiveData<Int>()
    private var retry: (() -> Any)? = null // function reference for the retry event

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        updateState(State.LOADING)
        totalCount.postValue(null)
        runBlocking {
            try {
                val response = service.getUsers(q = "$query in:login", page = 1, pageSize = 25)
                when{
                    response.isSuccessful -> {
                        updateState(State.DONE)
                        retry = null
                        val listing = response.body()
                        val users = listing?.children
                        totalCount.postValue(listing?.totalCount)
                        callback.onResult(users!!, null, 2)
                    }
                    else ->  {
                        updateState(State.ERROR)
                        retry = { loadInitial(params, callback) }
                    }
                }

            }catch (exception : Exception){
                updateState(State.ERROR)
                retry = { loadInitial(params, callback) }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        updateState(State.LOADING)
        runBlocking {
            try {
                val response = service.getUsers(
                    q = "$query in:login",
                    pageSize = 25,
                    page = params.key
                )
                when{
                    response.isSuccessful -> {
                        updateState(State.DONE)
                        retry = null
                        val listing = response.body()
                        callback.onResult(listing?.children ?: listOf(), params.key + 1)
                    }
                    else ->  {
                        updateState(State.ERROR)
                        retry = { loadAfter(params, callback) }
                    }
                }

            }catch (exception : Exception){
                updateState(State.ERROR)
                retry = { loadAfter(params, callback) }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) { }

}