package org.muhammadsayed.bookstorecmp.data.mappers


import org.muhammadsayed.bookstorecmp.data.data_source.remote.response.BookDTO
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel
import kotlin.random.Random


fun BookDTO.mapToDomainModel(): BookDomainModel {
    return BookDomainModel(
        id = work.key ?: "-1",
        title = work.title ?: "",
        subtitle = "",
        type = "Novel",
        price = Random.nextInt(500).toString(),
        image = work.coverEditionKey?.getCoverImage() ?: "",
        author = work.authorNames?.joinToString() ?: ""
    )
}

fun String.getCoverImage() = "https://covers.openlibrary.org/b/olid/$this-M.jpg"


fun List<BookDTO>.fromDTOList(): List<BookDomainModel> {
    return map { it.mapToDomainModel() }
}










