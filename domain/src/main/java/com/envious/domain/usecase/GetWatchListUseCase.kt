package com.envious.domain.usecase

import com.envious.domain.model.WatchItem
import com.envious.domain.util.Result

interface GetWatchListUseCase {
    suspend operator fun invoke(isFromRemote: Boolean, page: Int): Result<List<WatchItem>>
}
