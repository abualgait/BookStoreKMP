package org.gdg.fraud_cmp_app.presentation

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.gdg.fraud_cmp_app.presentation.home.HomeScreenEvents
import org.gdg.fraud_cmp_app.presentation.home.HomeScreenState
import org.gdg.fraud_cmp_app.presentation.home.HomeViewModel
import org.gdg.fraud_cmp_app.testdoubles.FakeAppRepository
import org.gdg.fraud_cmp_app.testdoubles.FakeDao
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var fakeDao: FakeDao
    private lateinit var fakeRepository: FakeAppRepository
    private lateinit var getCurrentlyReading: GetCurrentlyReading
    private lateinit var getAlreadyRead: GetAlreadyRead


    @BeforeTest
    fun setup() {
        fakeDao = FakeDao()
        fakeRepository = FakeAppRepository(fakeDao)
        getCurrentlyReading = GetCurrentlyReading(fakeRepository)
        getAlreadyRead = GetAlreadyRead(fakeRepository)

        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should emit currentlyReading state when call event getCurrentlyReadingData`() = runTest {
        val state = HomeScreenState()
        val sut = createViewModel()
        sut.onEvent(HomeScreenEvents.LoadCurrentlyReading)

        sut.state.test {
            assertEquals(state.copy(loading = false), awaitItem())
            assertEquals(state.copy(currentlyReading = fakeRepository.list), awaitItem())
        }
    }

    @Test
    fun `should emit getAlreadyReadData state when call event getCurrentlyReadingData`() = runTest {
        val state = HomeScreenState()
        val sut = createViewModel()
        sut.onEvent(HomeScreenEvents.LoadAlreadyRead)

        sut.state.test {
            assertEquals(state.copy(loading = false), awaitItem())
            assertEquals(state.copy(alreadyRead = fakeRepository.list), awaitItem())
        }
    }

    private fun createViewModel(): HomeViewModel {
        return HomeViewModel(
            getCurrentlyReading = getCurrentlyReading,
            getAlreadyRead = getAlreadyRead
        )
    }

}