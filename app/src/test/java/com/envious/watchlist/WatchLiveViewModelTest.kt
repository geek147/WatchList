package com.envious.watchlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.envious.data.local.dao.WatchListDao
import com.envious.data.local.entity.WatchItemEntity
import com.envious.domain.model.WatchItem
import com.envious.domain.usecase.GetWatchListUseCase
import com.envious.watchlist.ui.watch.WatchListContract
import com.envious.watchlist.ui.watch.WatchListViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WatchLiveViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var getWatchListUseCase = mockk<GetWatchListUseCase>()

    private var watchListDao: WatchListDao = mockk()

    private val observedStateList = mutableListOf<WatchListContract.State>()
    private val observerState = mockk<Observer<WatchListContract.State>>()
    private val slotState = slot<WatchListContract.State>()

    private val viewModel = WatchListViewModel(getWatchListUseCase)

    private val testDispatcher = TestCoroutineDispatcher()

    val watchItemEntity = WatchItemEntity(
        id = "1234",
        fullName = "Stagger",
        shortName = "STG",
        price = 1100.00,
        changePrice24 = 12.05
    )

    val watchItem = WatchItem(
        id = "1234",
        fullName = "Stagger",
        shortName = "STG",
        price = 1100.00,
        changePrice24 = 12.05
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        viewModel.state.observeForever(observerState)

        every {
            observerState.onChanged(capture(slotState))
        } answers {
            observedStateList.add(slotState.captured)
        }
    }

    @After
    fun tearDown() {
        observedStateList.clear()

        viewModel.state.removeObserver(observerState)
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `onGetFirstInit getDataFromLocal loading set display state to loading`() {
        coEvery {
            watchListDao.getWatchListLocal()
        } returns listOf(watchItemEntity)

        viewModel.onIntentReceived(
            WatchListContract.Intent.GetFirstData
        )

        assertEquals(observedStateList.first().viewState, WatchListContract.ViewState.Loading)
    }
}
