package com.envious.data.mapper

import com.envious.data.remote.response.WatchListResponse
import com.envious.domain.model.WatchItem

class WatchItemRemoteMapper : BaseMapperRepository<WatchListResponse.Data, WatchItem> {
    override fun transform(item: WatchListResponse.Data): WatchItem = WatchItem(
        id = item.coinInfo?.id.orEmpty(),
        shortName = item.coinInfo?.name.orEmpty(),
        fullName = item.coinInfo?.fullName.orEmpty(),
        price = item.rAW?.uSD?.pRICE ?: 0.00,
        changePrice24 = item.rAW?.uSD?.cHANGE24HOUR ?: 0.00
    )

    override fun transformToRepository(item: WatchItem): WatchListResponse.Data =
        WatchListResponse.Data()
}
