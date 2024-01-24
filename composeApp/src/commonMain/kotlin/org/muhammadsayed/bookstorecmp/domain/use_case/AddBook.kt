package org.muhammadsayed.bookstorecmp.domain.use_case


import kotlinx.coroutines.flow.Flow
import org.muhammadsayed.bookstorecmp.domain.DataState
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel
import org.muhammadsayed.bookstorecmp.domain.repository.AppRepository

class AddBook(
    private val repository: AppRepository
) {

    suspend operator fun invoke(book:BookDomainModel)  {
        return repository.addBook(book)
    }
}