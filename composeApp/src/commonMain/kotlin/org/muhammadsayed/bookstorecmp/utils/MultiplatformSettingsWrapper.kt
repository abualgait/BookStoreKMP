package org.muhammadsayed.bookstorecmp.utils

import com.russhwolf.settings.ObservableSettings

expect class MultiplatformSettingsWrapper {
    fun createSettings(): ObservableSettings
}
