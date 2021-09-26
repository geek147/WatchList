package com.envious.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.envious.data.local.entity.WatchItemEntity

@Dao
interface WatchListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWatchItem(watchItem: WatchItemEntity)

    @Query("SELECT * FROM watch_list_table")
    suspend fun getWatchListLocal(): List<WatchItemEntity>

    @Query("DELETE FROM watch_list_table")
    suspend fun deleteAllWatchList()
}
