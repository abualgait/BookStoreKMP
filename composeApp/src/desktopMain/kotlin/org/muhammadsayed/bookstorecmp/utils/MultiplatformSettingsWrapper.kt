package org.muhammadsayed.bookstorecmp.utils

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import java.util.prefs.Preferences

actual class MultiplatformSettingsWrapper {
    actual fun createSettings(): ObservableSettings {
        val delegate = Preferences.userRoot().node(this::class.java.name)
        return PreferencesSettings(delegate = delegate)
    }
}
