package org.muhammadsayed.bookstorecmp.data.repository


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.muhammadsayed.bookstorecmp.data.data_source.local.dao.BookDao
import org.muhammadsayed.bookstorecmp.data.data_source.remote.response.BookDetailsData
import org.muhammadsayed.bookstorecmp.data.data_source.remote.response.BookSearchResultsResponse
import org.muhammadsayed.bookstorecmp.data.data_source.remote.response.ReadingEnteriesResponse
import org.muhammadsayed.bookstorecmp.data.mappers.fromDTOList
import org.muhammadsayed.bookstorecmp.data.mappers.fromEntityList
import org.muhammadsayed.bookstorecmp.domain.DataState
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel
import org.muhammadsayed.bookstorecmp.domain.model.BookSearchDomainModel
import org.muhammadsayed.bookstorecmp.domain.repository.AppRepository

class AppRepositoryImpl(
    private val dao: BookDao,
    private val httpClient: HttpClient,
) : AppRepository {


    override suspend fun addBook(book: BookDomainModel) {
        dao.saveBook(book)
    }

    override suspend fun deleteBook(book: BookDomainModel) {
        dao.deleteBook(book.id.toInt())
    }

    override suspend fun getBooks(): Flow<List<BookDomainModel>> {
        return dao.getAllBooks().map {
            it.fromEntityList()
        }
    }

    override fun getSearchResults(query: String): Flow<DataState<List<BookSearchDomainModel>>> =
        flow {
            try {
                emit(DataState.Loading)
                val response = httpClient.get("search.json") {
                    parameter("q", query)

                }.body<BookSearchResultsResponse>()
                emit(DataState.Success(response.docs.fromDTOList()))
            } catch (e: Exception) {
                emit(DataState.Error(e.message ?: "Unknown error"))
            }
        }

    override suspend fun getCurrentlyReadingBooks(): Flow<DataState<List<BookDomainModel>>> = flow {
        try {
            emit(DataState.Loading)
            val response = httpClient.get("people/mekBot/books/currently-reading.json")
                .body<ReadingEnteriesResponse>()
            emit(DataState.Success(response.readingLogEntries.fromDTOList()))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun getAlreadyReadBooks(): Flow<DataState<List<BookDomainModel>>> = flow {
        try {
            emit(DataState.Loading)
            val response = httpClient.get("people/mekBot/books/already-read.json")
                .body<ReadingEnteriesResponse>()
            emit(DataState.Success(response.readingLogEntries.fromDTOList()))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun getBookDetails(key: String): Flow<DataState<BookDetailsData>> = flow {
        try {
            emit(DataState.Loading)
            val response = httpClient.get("works/${key}.json")
                .body<BookDetailsData>()
            emit(DataState.Success(response))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
        }
    }

}