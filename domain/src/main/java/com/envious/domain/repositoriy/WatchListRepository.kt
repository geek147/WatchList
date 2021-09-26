package com.envious.domain.repositoriy

import com.envious.domain.model.WatchItem
import com.envious.domain.util.Result

interface WatchListRepository {
    suspend fun getWatchList(isFromRemote: Boolean, page: Int): Result<List<WatchItem>>
}