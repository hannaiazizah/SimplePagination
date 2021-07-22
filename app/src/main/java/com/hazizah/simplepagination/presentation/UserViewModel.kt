package com.hazizah.simplepagination.presentation

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.hazizah.simplepagination.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
): ViewModel() {
    private val _usersListing = MutableLiveData<Listing<User>>()
    val users: LiveData<PagedList<User>> = Transformations.switchMap(_usersListing) { it.pagedList }

    private val _productListing = MutableLiveData<Listing<Product>>()
    val products: LiveData<PagedList<Product>> = Transformations.switchMap(_productListing) { it.pagedList }

    val networkState: LiveData<State> = Transformations.switchMap(_productListing) { it.networkState }
    val totalCount: LiveData<Int> = Transformations.switchMap(_productListing) { it.totalCount }

    fun searchUser(query: String) {
        val data = userRepository.searchUsers(query)
        _usersListing.postValue(data)
    }

    fun retry() {
        _productListing.value?.retry?.invoke()
    }

    fun searchProduct(query: String) {
        val data = productRepository.searchProduct(query)
        _productListing.postValue(data)

        viewModelScope.launch(Dispatchers.Main) {
            suspend fun a1(): Boolean = true
            suspend fun a2() = 1


        }
    }

}
