package org.muhammadsayed.bookstorecmp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import org.koin.compose.koinInject
import org.muhammadsayed.bookstorecmp.presentation.getstarted.GetStartedScreen
import org.muhammadsayed.bookstorecmp.presentation.cart.CartScreen

@Composable
fun Navigation(
    navigator: Navigator,
    onLocalChange: (String) -> Unit
) {

    val route = NavigationItem.GetStarted.route

    NavHost(
        navigator = navigator,
        initialRoute = route

    ) {
        scene(NavigationItem.GetStarted.route) {
            GetStartedScreen(
                navigator = navigator,
                onLocalChange = onLocalChange
            )
        }
        scene(NavigationItem.Cart.route) {
            CartScreen(navigator = navigator)
        }
    }


}
