package org.muhammadsayed.bookstorecmp.presentation

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.muhammadsayed.bookstorecmp.presentation.settings.SettingsState
import org.muhammadsayed.bookstorecmp.presentation.settings.SettingsViewModel
import org.muhammadsayed.bookstorecmp.testdoubles.FakeSettingsRepository
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {
    private val settingsRepository = FakeSettingsRepository()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should showGetStarted state true`() = runTest {
        val state = SettingsState()
        val sut = createViewModel()

        sut.state.test {
            awaitEvent()
            assertEquals(state.copy(showGetStarted = true), awaitItem())
        }
    }


    @Test
    fun `should showGetStarted state false when call savePreferenceGetStarted`() = runTest {
        val state = SettingsState()
        val sut = createViewModel()
        sut.savePreferenceGetStarted()
        sut.state.test {
            awaitEvent()
            assertEquals(state.copy(showGetStarted = false), awaitItem())
        }
    }

    private fun createViewModel(): SettingsViewModel {
        return SettingsViewModel(
            settingsRepository
        )
    }

}