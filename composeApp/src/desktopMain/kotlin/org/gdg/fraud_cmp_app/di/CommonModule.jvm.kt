package org.gdg.fraud_cmp_app.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.gdg.fraud_cmp_app.utils.DatabaseDriverFactory
import org.gdg.fraud_cmp_app.utils.MultiplatformSettingsWrapper

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
    single { MultiplatformSettingsWrapper().createSettings() }
}