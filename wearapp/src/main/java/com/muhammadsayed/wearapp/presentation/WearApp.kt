package com.muhammadsayed.wearapp.presentation

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.muhammadsayed.bookstorecmp.di.initKoin

class WearApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(level = Level.INFO)
            androidContext(androidContext = this@WearApp)
        }
    }
}