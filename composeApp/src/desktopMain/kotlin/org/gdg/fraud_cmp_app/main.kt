package org.gdg.fraud_cmp_app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BookStore",
    ) {
        initKoin()
        App()
    }
}