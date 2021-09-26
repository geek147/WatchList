package com.envious.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.envious.data.util.Constants.WATCH_LIST_TABLE_NAME

@Entity(tableName = WATCH_LIST_TABLE_NAME)
data class WatchItemEntity(

    @PrimaryKey
    val id: String,
    val shortName: String? = "",
    val fullName: String? = "",
    val price: Double = 0.00,
    val changePrice24: Double = 0.00
)
