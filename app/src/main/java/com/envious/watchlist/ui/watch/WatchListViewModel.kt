package com.envious.watchlist.ui.watch

import androidx.lifecycle.viewModelScope
import com.envious.domain.usecase.GetWatchListUseCase
import com.envious.domain.util.Result
import com.envious.watchlist.base.BaseViewModel
import com.envious.watchlist.util.State
import com.envious.watchlist.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WatchListViewModel(
    private val getWatchListUseCase: GetWatchListUseCase
) : BaseViewModel<WatchListContract.Intent, WatchListContract.State>(WatchListContract.State()) {

    override fun onIntentReceived(intent: WatchListContract.Intent) {
        when (intent) {
            WatchListContract.Intent.GetFirstData -> getLocalWatchList()
            is WatchListContract.Intent.LoadNext -> getRemoteWatchList(intent.page)
        }
    }

    private fun getRemoteWatchList(page: Int) {
        setState { copy(viewState = WatchListContract.ViewState.Loading) }
        viewModelScope.launch {
            when (
                val result =
                    withContext(Dispatchers.IO) { getWatchListUseCase(true, page) }
            ) {
                is Result.Success -> {
                    if (result.data.isNotEmpty()) {
                        if (page == 0) {
                            setState {
                                copy(
                                    viewState = WatchListContract.ViewState.SuccessFirstInit,
                                    listItem = result.data
                                )
                            }
                        } else {
                            setState {
                                copy(
                                    viewState = WatchListContract.ViewState.SuccessLoadMore,
                                    listItem = result.data
                                )
                            }
                        }
                    } else {
                        setState {
                            copy(
                                viewState = WatchListContract.ViewState.EmptyList,
                            )
                        }
                    }
                }

                is Result.Error -> {
                    if (page == 0) {
                        setState {
                            copy(
                                viewState = WatchListContract.ViewState.ErrorFirstInit
                            )
                        }
                    } else {
                        setState {
                            copy(
                                viewState = WatchListContract.ViewState.ErrorLoadMore
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getLocalWatchList() {
        setState { copy(viewState = WatchListContract.ViewState.Loading) }
        viewModelScope.launch {
            when (
                val result =
                    withContext(Dispatchers.IO) { getWatchListUseCase(false, -1) }
            ) {
                is Result.Success -> {
                    if (result.data.isNotEmpty()) {
                        setState {
                            copy(
                                viewState = WatchListContract.ViewState.SuccessFirstInit,
                                listItem = result.data
                            )
                        }
                    } else {
                        getRemoteWatchList(0)
                    }
                }
            }
        }
    }
}
