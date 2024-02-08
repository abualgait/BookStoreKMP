package org.muhammadsayed.bookstorecmp.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun savePreferenceGetStarted(key: String, isFirstTime: Boolean)
    suspend fun getIsGetStartedShown(key: String): Flow<Boolean>
}
