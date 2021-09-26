package com.envious.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.envious.data.local.dao.WatchListDao
import com.envious.data.local.entity.WatchItemEntity

@Database(entities = [WatchItemEntity::class], version = 1, exportSchema = false)
abstract class WatchListDatabase : RoomDatabase() {

    abstract val watchListDao: WatchListDao
}
