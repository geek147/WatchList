package com.envious.data.mapper

import com.envious.data.local.entity.WatchItemEntity
import com.envious.domain.model.WatchItem

class WatchListLocalMapper : BaseMapperRepository<WatchItemEntity, WatchItem> {
    override fun transform(item: WatchItemEntity): WatchItem = WatchItem(
        item.id, item.shortName ?: "", item.fullName ?: "", item.price, item.changePrice24
    )

    override fun transformToRepository(item: WatchItem): WatchItemEntity = WatchItemEntity(
        id = item.id,
        shortName = item.shortName,
        fullName = item.fullName,
        price = item.price,
        changePrice24 = item.changePrice24
    )
}
