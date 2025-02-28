package org.gdg.fraud_cmp_app.data.data_source.settings

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.gdg.fraud_cmp_app.testdoubles.FakeSettingsRepository
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
