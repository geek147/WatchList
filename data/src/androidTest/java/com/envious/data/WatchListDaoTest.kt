package com.envious.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.envious.data.local.db.WatchListDatabase
import com.envious.data.local.entity.WatchItemEntity
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class WatchListDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: WatchListDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WatchListDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertWatchItemTestAndGetAgainResultSame() {
        val watchItemEntity = WatchItemEntity(
            id = "1234",
            fullName = "Stagger",
            shortName = "STG",
            price = 1100.00,
            changePrice24 = 12.05
        )

        lateinit var returnedWatchItem: WatchItemEntity
        runBlockingTest {
            database.watchListDao.saveWatchItem(watchItemEntity)
            returnedWatchItem = database.watchListDao.getWatchListLocal().first()
        }

        assertEquals(watchItemEntity, returnedWatchItem)
    }
}
