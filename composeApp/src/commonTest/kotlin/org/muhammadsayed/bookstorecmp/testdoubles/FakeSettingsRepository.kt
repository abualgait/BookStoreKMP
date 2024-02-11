package org.muhammadsayed.bookstorecmp.testdoubles

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.MapSettings
import com.russhwolf.settings.coroutines.getBooleanFlow
import kotlinx.coroutines.flow.Flow
import org.muhammadsayed.bookstorecmp.domain.repository.SettingsRepository

class FakeSettingsRepository : SettingsRepository {
    private val settings = MapSettings()
    override suspend fun savePreferenceGetStarted(key: String, isFirstTime: Boolean) {
        settings.putBoolean(key, isFirstTime)
    }

    @OptIn(ExperimentalSettingsApi::class)
    override suspend fun getIsGetStartedShown(key: String): Flow<Boolean> {
        return settings.getBooleanFlow(key, true)
    }

}