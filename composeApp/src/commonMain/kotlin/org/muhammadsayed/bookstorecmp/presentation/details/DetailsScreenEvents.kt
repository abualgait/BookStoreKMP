package org.muhammadsayed.bookstorecmp.presentation.details

import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel

sealed class DetailsScreenEvents {
    data class AddBook(val book: BookDomainModel) : DetailsScreenEvents()
    data class GetBookDetails(val bookId: String) : DetailsScreenEvents()
}

