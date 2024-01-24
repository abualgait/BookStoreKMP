package org.muhammadsayed.bookstorecmp.data.data_source.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BookDetailsData(
    @SerialName("title")
    val title: String? = null,
    @SerialName("subtitle")
    val subtitle: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("subjects")
    val subjects: List<String>? = null,
    @SerialName("key")
    val key: String? = null,
)


