package org.muhammadsayed.bookstorecmp.domain.use_case


import kotlinx.coroutines.flow.Flow
import org.muhammadsayed.bookstorecmp.data.data_source.remote.response.BookDetailsData
import org.muhammadsayed.bookstorecmp.domain.DataState
import org.muhammadsayed.bookstorecmp.domain.repository.AppRepository

class GetBookDetails(
    private val repository: AppRepository
) {

    suspend operator fun invoke(bookId:String): Flow<DataState<BookDetailsData>> {
        return repository.getBookDetails(key = bookId)
    }
}