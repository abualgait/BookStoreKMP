package org.muhammadsayed.bookstorecmp.presentation.cart

import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel

sealed class CartScreenEvents {
    data class UpdateBookQty(val book: BookDomainModel) : CartScreenEvents()
    data class DeleteBook(val book: BookDomainModel) : CartScreenEvents()
    data object LoadCartItems : CartScreenEvents()
}

