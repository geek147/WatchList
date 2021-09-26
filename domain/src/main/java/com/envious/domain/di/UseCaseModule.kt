package com.envious.domain.di

import com.envious.domain.usecase.GetWatchListUseCase
import com.envious.domain.usecase.GetWatchListUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    factory<GetWatchListUseCase> { GetWatchListUseCaseImpl(get()) }
}
