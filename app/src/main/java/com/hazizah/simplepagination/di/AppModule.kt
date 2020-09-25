package com.hazizah.simplepagination.di

import com.hazizah.simplepagination.data.api.GithubService
import com.hazizah.simplepagination.data.repository.UserRepositoryImpl
import com.hazizah.simplepagination.domain.UserRepository
import com.hazizah.simplepagination.presentation.UserViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.Executors

val appModule = module {
    viewModel {
        UserViewModel(
            repository = get()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(
            service = GithubService.create(),
            retryExecutor = Executors.newSingleThreadExecutor()
        )
    }
}