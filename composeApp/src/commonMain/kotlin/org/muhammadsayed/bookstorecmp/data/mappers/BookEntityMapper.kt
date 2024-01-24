package org.muhammadsayed.bookstorecmp.data.mappers


import org.muhammadsayed.bookstorecmp.data.datasource.local.BookEntity
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel


fun BookEntity.mapToDomainModel(): BookDomainModel {
    return BookDomainModel(
        id = id.toString(),
        title = title,
        subtitle = subtitle,
        type = type,
        price = price.toString(),
        image = image,
        author = author,
        qty = qty
    )
}


fun List<BookEntity>.fromEntityList(): List<BookDomainModel> {
    return map { it.mapToDomainModel() }
}










