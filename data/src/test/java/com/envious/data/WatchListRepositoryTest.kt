package com.envious.data // ktlint-disable filename
// ktlint-disable filename

import com.envious.data.local.dao.WatchListDao
import com.envious.data.local.entity.WatchItemEntity
import com.envious.data.remote.WatchListApiService
import com.envious.data.remote.response.WatchListResponse
import com.envious.data.repository.WatchListRepositoryImpl
import com.envious.data.util.Constants
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class WatchListRepositoryTest {

    private val watchListApiService: WatchListApiService = mockk()

    private var watchListDao: WatchListDao = mockk()

    private lateinit var repositoryTest: WatchListRepositoryImpl

    private val page: Int = 0

    val watchItemEntity = WatchItemEntity(
        id = "1234",
        fullName = "Stagger",
        shortName = "STG",
        price = 1100.00,
        changePrice24 = 12.05
    )

    val watchListResponse: WatchListResponse by lazy {
        WatchListResponse(
            data = listWatchItem,
            hasWarning = false,
            message = "",
            type = 2
        )
    }

    val listWatchItem: List<WatchListResponse.Data> = listOf<com.envious.data.remote.response.WatchListResponse.Data>(
        com.envious.data.remote.response.WatchListResponse.Data(
            coinInfo = com.envious.data.remote.response.WatchListResponse.Data.CoinInfo(
                id = "1234",
                fullName = "Stagger",
                name = "STG"
            ),
            com.envious.data.remote.response.WatchListResponse.Data.RAW(
                com.envious.data.remote.response.WatchListResponse.Data.RAW.USD(
                    pRICE = 1100.00,
                    cHANGE24HOUR = 12.05
                )
            )
        )
    )

    @Before
    fun setUp() {
        repositoryTest = WatchListRepositoryImpl(watchListApiService, watchListDao)
    }

    @Test
    fun getWatchListFromRemote_returnSuccessWatchList() {

        coEvery {
            watchListApiService.getWatchList(any(), any(), any())
        } returns Response.success(watchListResponse)

        coEvery {
            watchListDao.saveWatchItem(any())
        } returns Unit

        runBlockingTest {
            repositoryTest.getWatchList(true, page)
        }

        coVerify {
            watchListApiService.getWatchList(page, Constants.LIMIT_ITEM, Constants.TSYM)
            watchListDao.saveWatchItem(watchItemEntity)
        }
    }

    @Test
    fun getWatchListFromLocal_returnCachedWatchListsuccess() = runBlockingTest {

        coEvery {
            watchListDao.getWatchListLocal()
        } returns listOf(watchItemEntity)

        runBlockingTest {
            repositoryTest.getWatchList(false, page)
        }

        coVerify {
            watchListDao.getWatchListLocal()
        }
    }
}
