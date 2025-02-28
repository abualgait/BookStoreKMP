package com.muhammadsayed.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.gdg.fraud_cmp_app.App
import org.gdg.fraud_cmp_app.di.initKoin
import org.gdg.fraud_cmp_app.theme.AppTheme
import org.gdg.fraud_cmp_app.utils.ContextUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextUtils.setContext(context = this)
        initKoin {
            androidLogger(level = Level.INFO)
            androidContext(androidContext = this@MainActivity)
        }
        setContent {
            AppTheme {
                App()
            }

        }
    }
}
