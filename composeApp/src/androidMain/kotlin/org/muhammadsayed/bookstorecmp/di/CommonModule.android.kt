package org.muhammadsayed.bookstorecmp.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.muhammadsayed.bookstorecmp.utils.DatabaseDriverFactory
import org.muhammadsayed.bookstorecmp.utils.MultiplatformSettingsWrapper

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory(context = get()) }
    single { MultiplatformSettingsWrapper().createSettings() }
}