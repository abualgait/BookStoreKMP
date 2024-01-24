package org.muhammadsayed.bookstorecmp.data.data_source.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BookSearchResultsResponse(
    @SerialName("docs")
    val docs: List<DocDTO>,
)

@Serializable
data class DocDTO(
    @SerialName("key")
    val key: String,
    @SerialName("title")
    val title: String,
)

