package org.gdg.fraud_cmp_app.utils

import com.russhwolf.settings.ObservableSettings

expect class MultiplatformSettingsWrapper {
    fun createSettings(): ObservableSettings
}
