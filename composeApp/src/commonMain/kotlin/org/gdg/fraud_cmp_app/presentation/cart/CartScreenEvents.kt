package org.gdg.fraud_cmp_app.presentation.cart

import org.gdg.fraud_cmp_app.domain.model.SmsDomainModel

sealed class CartScreenEvents {
    data class UpdateBookQty(val book: SmsDomainModel) : CartScreenEvents()
    data class DeleteBook(val book: SmsDomainModel) : CartScreenEvents()
    data object LoadCartItems : CartScreenEvents()
}

