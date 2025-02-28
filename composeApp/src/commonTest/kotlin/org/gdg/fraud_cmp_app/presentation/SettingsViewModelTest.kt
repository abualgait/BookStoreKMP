package org.gdg.fraud_cmp_app.presentation

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.gdg.fraud_cmp_app.presentation.settings.SettingsState
import org.gdg.fraud_cmp_app.presentation.settings.SettingsViewModel
import org.gdg.fraud_cmp_app.testdoubles.FakeSettingsRepository
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