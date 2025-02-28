package org.gdg.fraud_cmp_app.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun savePreferenceGetStarted(key: String, isFirstTime: Boolean)
    suspend fun getIsGetStartedShown(key: String): Flow<Boolean>
}
