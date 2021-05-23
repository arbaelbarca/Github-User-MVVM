package com.arbaelbarca.github_user_project.di

import com.arbaelbarca.github_user_project.datasource.network.ApiService
import com.arbaelbarca.github_user_project.datasource.network.AuthInterceptor
import com.arbaelbarca.github_user_project.datasource.network.createWebService
import com.arbaelbarca.github_user_project.datasource.network.provideOkHttpClient
import com.arbaelbarca.github_user_project.db.AppDatabase
import com.arbaelbarca.github_user_project.presentation.viewmodel.ViewModelUser
import com.arbaelbarca.github_user_project.repository.IRepositoryUser
import com.arbaelbarca.github_user_project.repository.RepositoryUserImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { provideOkHttpClient(get(), get()) }
    single { createWebService<ApiService>(get()) }
    single { AuthInterceptor() }
}

val viewModelModule = module {
    viewModel {
        ViewModelUser(
            get()
        )
    }
}

val dataBaseModule = module {
    single { AppDatabase.getInstance(androidApplication()) }
    single { get<AppDatabase>().userDao() }
}


val repositoryModule = module {
    single<IRepositoryUser> { RepositoryUserImpl(get(), get()) }
}

val myAppModule = listOf(networkModule, repositoryModule, viewModelModule, dataBaseModule)