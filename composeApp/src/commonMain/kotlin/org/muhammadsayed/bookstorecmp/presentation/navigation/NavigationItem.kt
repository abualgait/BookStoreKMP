package org.muhammadsayed.bookstorecmp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector?
) {
    data object GetStarted : NavigationItem("/getStarted", "GetStarted", null)
    data object Cart : NavigationItem("/cart", "Cart", Icons.Rounded.ShoppingCart)
}
