package com.envious.domain

import com.envious.domain.model.WatchItem
import com.envious.domain.repositoriy.WatchListRepository
import com.envious.domain.usecase.GetWatchListUseCaseImpl
import com.envious.domain.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetWatchListUseCaseTest {

    private val repository: WatchListRepository = mockk()
    private var getWatchList: GetWatchListUseCaseImpl = mockk()
    private val getFromRemote: Boolean = false
    private val page: Int = 0

    @Before
    fun setUp() {
        getWatchList = GetWatchListUseCaseImpl(repository)
    }

    @Test
    fun getWatchList_call_getWatchListRepository() {
        val emptyList = emptyList<WatchItem>()

        coEvery {
            repository.getWatchList(any(), any())
        } returns Result.Success(emptyList)

        runBlockingTest {
            getWatchList(getFromRemote, page)
        }

        coVerify {
            repository.getWatchList(getFromRemote, page)
        }
    }
}
