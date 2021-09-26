package com.envious.domain.usecase

import com.envious.domain.model.WatchItem
import com.envious.domain.repositoriy.WatchListRepository
import com.envious.domain.util.Result

class GetWatchListUseCaseImpl(private val repository: WatchListRepository) : GetWatchListUseCase {
    override suspend fun invoke(isFromRemote: Boolean, page: Int): Result<List<WatchItem>> =
        repository.getWatchList(isFromRemote, page)
}
