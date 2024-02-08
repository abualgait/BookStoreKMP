package org.muhammadsayed.bookstorecmp.utils

import android.content.Context
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings

actual class MultiplatformSettingsWrapper {
    private val context = ContextUtils.context

    actual fun createSettings(): ObservableSettings {
        val sharedPreferences =
            context.getSharedPreferences("bookstore_preferences", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate = sharedPreferences)
    }
}
