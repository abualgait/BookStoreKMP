package org.muhammadsayed.bookstorecmp.data.mappers


import org.muhammadsayed.bookstorecmp.data.data_source.remote.response.DocDTO
import org.muhammadsayed.bookstorecmp.domain.model.BookSearchDomainModel


fun DocDTO.mapToDomainModel(): BookSearchDomainModel {
    return BookSearchDomainModel(
        id = key,
        title = title,

    )
}



fun List<DocDTO>.fromDTOList(): List<BookSearchDomainModel> {
    return map { it.mapToDomainModel() }
}










