package org.gdg.fraud_cmp_app.presentation.cart

import org.gdg.fraud_cmp_app.domain.model.SmsDomainModel

data class CartScreenState(
    val loading: Boolean = false,
    val smsList: List<SmsDomainModel>? = emptyList(),
    val subTotal: Long? = 0,
    val total: Long? = 0,

    )

