package org.gdg.fraud_cmp_app.presentation.navigation

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import org.gdg.fraud_cmp_app.presentation.getstarted.GetStartedScreen
import org.gdg.fraud_cmp_app.presentation.cart.CartScreen

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
