package org.muhammadsayed.bookstorecmp.data.data_source.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ReadingEnteriesResponse(
    @SerialName("reading_log_entries")
    val readingLogEntries: List<BookDTO>,
)

@Serializable
data class BookDTO(
    @SerialName("work")
    val work: WorkDTO,
    @SerialName("logged_date")
    val loggedDate: String,
)

@Serializable
data class WorkDTO(
    @SerialName("title")
    val title: String? = "",
    @SerialName("key")
    val key: String?,
    @SerialName("author_names")
    val authorNames: List<String>? = emptyList(),
    @SerialName("first_publish_year")
    val firstPublishYear: String?,
    @SerialName("cover_edition_key")
    val coverEditionKey: String?
)

