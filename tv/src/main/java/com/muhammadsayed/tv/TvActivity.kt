package com.muhammadsayed.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.muhammadsayed.bookstorecmp.App
import org.muhammadsayed.bookstorecmp.di.initKoin
import org.muhammadsayed.bookstorecmp.theme.AppTheme
import org.muhammadsayed.bookstorecmp.utils.ContextUtils

class TvActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ContextUtils.setContext(context = this)
        initKoin {
            androidLogger(level = Level.NONE)
            androidContext(androidContext = this@TvActivity)
        }

        setContent {
            AppTheme {
                App()
            }
        }
    }
}

