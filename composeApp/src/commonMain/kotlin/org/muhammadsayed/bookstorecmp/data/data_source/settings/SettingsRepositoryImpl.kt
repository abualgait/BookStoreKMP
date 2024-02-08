package org.muhammadsayed.bookstorecmp.data.data_source.settings

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getBooleanFlow
import kotlinx.coroutines.flow.Flow
import org.muhammadsayed.bookstorecmp.domain.repository.SettingsRepository

@ExperimentalSettingsApi
class SettingsRepositoryImpl(private val observableSettings: ObservableSettings) :
    SettingsRepository {

    override suspend fun savePreferenceGetStarted(key: String, isFirstTime: Boolean) {
        observableSettings.putBoolean(key, isFirstTime)
    }

    override suspend fun getIsGetStartedShown(key: String): Flow<Boolean> {
        return observableSettings.getBooleanFlow(key, true)
    }
}
