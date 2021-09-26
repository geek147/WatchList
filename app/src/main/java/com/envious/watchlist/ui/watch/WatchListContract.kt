package com.envious.watchlist.ui.watch

import com.envious.domain.model.WatchItem

class WatchListContract {
    sealed class Intent {
        object GetFirstData : Intent()
        data class LoadNext(val page: Int) : Intent()
    }

    data class State(
        val viewState: ViewState = ViewState.Idle,
        val listItem: List<WatchItem> = listOf()
    )

    sealed class ViewState {
        object Idle : ViewState()
        object Loading : ViewState()
        object SuccessFirstInit : ViewState()
        object EmptyList : ViewState()
        object SuccessLoadMore : ViewState()
        object ErrorFirstInit : ViewState()
        object ErrorLoadMore : ViewState()
    }
}
