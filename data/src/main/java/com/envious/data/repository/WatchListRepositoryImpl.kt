package com.envious.data.repository

import com.envious.data.local.dao.WatchListDao
import com.envious.data.local.entity.WatchItemEntity
import com.envious.data.mapper.WatchItemRemoteMapper
import com.envious.data.mapper.WatchListLocalMapper
import com.envious.data.remote.WatchListApiService
import com.envious.data.util.Constants.LIMIT_ITEM
import com.envious.data.util.Constants.TSYM
import com.envious.domain.model.WatchItem
import com.envious.domain.repositoriy.WatchListRepository
import com.envious.domain.util.Result

class WatchListRepositoryImpl(
    private val watchListApiService: WatchListApiService,
    private val watchListDao: WatchListDao
) : WatchListRepository {

    override suspend fun getWatchList(isFromRemote: Boolean, page: Int): Result<List<WatchItem>> {
        return when {
            isFromRemote -> {
                val watchListResult = watchListApiService.getWatchList(page, LIMIT_ITEM, TSYM)
                if (watchListResult.isSuccessful) {
                    val mapperRemote = WatchItemRemoteMapper()
                    val remoteData = watchListResult.body()
                    if (remoteData != null) {
                        val listData = mutableListOf<WatchItem>()
                        remoteData.data?.forEach { item ->
                            watchListDao.saveWatchItem(
                                WatchItemEntity(
                                    id = item?.coinInfo?.id.orEmpty(),
                                    shortName = item?.coinInfo?.name,
                                    fullName = item?.coinInfo?.fullName,
                                    price = item?.rAW?.uSD?.pRICE ?: 0.00,
                                    changePrice24 = item?.rAW?.uSD?.cHANGE24HOUR ?: 0.00
                                )
                            )
                            listData.add(mapperRemote.transform(item!!))
                        }
                        Result.Success(listData)
                    } else {
                        Result.Success(emptyList())
                    }
                } else {
                    Result.Error(Exception("Invalid data/failure"))
                }
            }
            else -> {
                val localData = watchListDao.getWatchListLocal()
                run {
                    val mapperLocal = WatchListLocalMapper()
                    val listData = mutableListOf<WatchItem>()
                    localData.forEach { item ->
                        listData.add(mapperLocal.transform(item))
                    }
                    Result.Success(listData)
                }
            }
        }
    }
}
