package org.muhammadsayed.bookstorecmp.presentation.home

import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel

data class HomeScreenState(
    val error: String? = null,
    val loading: Boolean = false,
    val alreadyReadLoading: Boolean = false,
    val currentlyReading: List<BookDomainModel>? = emptyList(),
    val alreadyRead: List<BookDomainModel>? = emptyList()

)

