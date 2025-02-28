package org.gdg.fraud_cmp_app

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import org.gdg.fraud_cmp_app.presentation.navigation.Navigation
import org.gdg.fraud_cmp_app.theme.AppTheme
import org.gdg.fraud_cmp_app.utils.changeLocale

@Composable
fun App() {
    PreComposeApp {

        AppTheme {

            val navigator = rememberNavigator()

            Scaffold() { paddingValues ->

                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Navigation(
                            navigator = navigator
                        ) {
                            changeLocale(it)
                        }
                    }
                }
            }
        }
    }

}



