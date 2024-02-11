package org.muhammadsayed.bookstorecmp.data.data_source.settings

import app.cash.turbine.test
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.MapSettings
import com.russhwolf.settings.coroutines.getBooleanFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.muhammadsayed.bookstorecmp.domain.repository.SettingsRepository
import org.muhammadsayed.bookstorecmp.testdoubles.FakeSettingsRepository
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue



class SettingsRepositoryTest {

    private val fakeSettingsRepository = FakeSettingsRepository()

    @Test
    fun `should savePreferenceGetStarted return false`() = runTest {
        fakeSettingsRepository.savePreferenceGetStarted("Key", false)
        val result = fakeSettingsRepository.getIsGetStartedShown("Key")

        result.test {
            val item = awaitItem()
            assertFalse(item)
        }
    }

    @Test
    fun `should savePreferenceGetStarted return true`() = runTest {
        fakeSettingsRepository.savePreferenceGetStarted("Key", true)
        val result = fakeSettingsRepository.getIsGetStartedShown("Key")

        result.test {
            val item = awaitItem()
            assertTrue(item)
        }
    }

}
