package com.envious.watchlist.util

import com.envious.domain.model.WatchItem

sealed class ViewState {
    data class Success(val list: List<WatchItem>) : ViewState()
    object Empty : ViewState()
    object Error : ViewState()
    object Loading : ViewState()
    object Initial : ViewState()
}

data class State(
    val viewState: ViewState
)