package com.hazizah.simplepagination.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.hazizah.simplepagination.domain.Listing
import com.hazizah.simplepagination.domain.State
import com.hazizah.simplepagination.domain.User
import com.hazizah.simplepagination.domain.UserRepository

class UserViewModel(
    private val repository: UserRepository
): ViewModel() {
    private val _usersListing = MutableLiveData<Listing<User>>()
    val users: LiveData<PagedList<User>> = Transformations.switchMap(_usersListing) { it.pagedList }
    val networkState: LiveData<State> = Transformations.switchMap(_usersListing) { it.networkState }
    val totalCount: LiveData<Int> = Transformations.switchMap(_usersListing) { it.totalCount }

    fun searchUser(query: String) {
        val data = repository.searchUsers(query)
        _usersListing.postValue(data)
    }

    fun retry() {
        _usersListing.value?.retry?.invoke()
    }

}