package com.envious.data.di

import com.envious.data.repository.WatchListRepositoryImpl
import com.envious.domain.repositoriy.WatchListRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<WatchListRepository> { WatchListRepositoryImpl(get(), get()) }
}