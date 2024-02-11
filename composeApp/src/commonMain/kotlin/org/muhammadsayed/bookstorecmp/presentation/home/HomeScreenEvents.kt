package org.muhammadsayed.bookstorecmp.presentation.home

sealed class HomeScreenEvents {
    data object LoadCurrentlyReading : HomeScreenEvents()
    data object LoadAlreadyRead : HomeScreenEvents()
}

