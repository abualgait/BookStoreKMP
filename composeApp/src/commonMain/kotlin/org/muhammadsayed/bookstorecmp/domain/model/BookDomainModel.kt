package org.muhammadsayed.bookstorecmp.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class BookDomainModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val type: String,
    val price: String,
    val image: String,
    val author: String,
    var qty: Long? = 1L
)
