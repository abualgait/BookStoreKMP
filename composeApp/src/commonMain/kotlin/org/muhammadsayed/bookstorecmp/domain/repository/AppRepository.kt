package org.muhammadsayed.bookstorecmp.domain.repository

import kotlinx.coroutines.flow.Flow
import org.muhammadsayed.bookstorecmp.data.data_source.remote.response.BookDetailsData
import org.muhammadsayed.bookstorecmp.domain.DataState
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel
import org.muhammadsayed.bookstorecmp.domain.model.BookSearchDomainModel

interface AppRepository {
    suspend fun addBook(book: BookDomainModel)
    suspend fun deleteBook(book: BookDomainModel)
    suspend fun getBooks(): Flow<List<BookDomainModel>>
    fun getSearchResults(query: String): Flow<DataState<List<BookSearchDomainModel>>>
    suspend fun getCurrentlyReadingBooks(): Flow<DataState<List<BookDomainModel>>>
    suspend fun getAlreadyReadBooks(): Flow<DataState<List<BookDomainModel>>>
    suspend fun getBookDetails(key:String): Flow<DataState<BookDetailsData>>
}