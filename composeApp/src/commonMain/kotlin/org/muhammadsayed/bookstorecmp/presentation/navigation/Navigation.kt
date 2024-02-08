package org.muhammadsayed.bookstorecmp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import org.koin.compose.koinInject
import org.muhammadsayed.bookstorecmp.presentation.cart.CartScreen
import org.muhammadsayed.bookstorecmp.presentation.details.DetailsScreen
import org.muhammadsayed.bookstorecmp.presentation.getstarted.GetStartedScreen
import org.muhammadsayed.bookstorecmp.presentation.home.HomeScreen
import org.muhammadsayed.bookstorecmp.presentation.settings.SettingsViewModel

@Composable
fun Navigation(
    navigator: Navigator,
    viewModel: SettingsViewModel = koinInject(),
    onLocalChange: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    state.showGetStarted?.let {
        val route = if (state.showGetStarted == true)
            NavigationItem.GetStarted.route
        else
            NavigationItem.Home.route

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
            scene(NavigationItem.Home.route) {
                HomeScreen(
                    navigator = navigator
                )
            }

            scene(NavigationItem.Categories.route) {

            }

            scene(NavigationItem.Cart.route) {
                CartScreen(navigator = navigator)
            }

            scene(NavigationItem.Account.route) {

            }

            scene(NavigationItem.Details.route) { backStackEntry ->
                backStackEntry.path<String>("id")?.let { bookId ->

                    val bookImage: String? = backStackEntry.query<String>("image")
                    DetailsScreen(
                        navigator = navigator,
                        bookId = bookId,
                        bookImage = bookImage ?: ""
                    )


                }
            }
        }
    }

}
