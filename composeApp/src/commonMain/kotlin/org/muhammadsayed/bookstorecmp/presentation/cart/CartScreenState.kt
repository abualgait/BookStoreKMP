package org.muhammadsayed.bookstorecmp.presentation.cart

import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel

data class CartScreenState(
    val loading: Boolean = false,
    val cartItems: List<BookDomainModel>? = emptyList(),
    val subTotal: Long? = 0,
    val total: Long? = 0,

)

