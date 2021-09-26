package com.envious.watchlist.di

import com.envious.watchlist.ui.watch.WatchListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        WatchListViewModel(get())
    }
}
