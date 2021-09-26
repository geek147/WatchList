package com.envious.watchlist.util

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

inline fun <T : View> T.showIf(condition: (T) -> Boolean) {
    visibility = if (condition(this)) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>