package org.muhammadsayed.bookstorecmp.testdoubles

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.muhammadsayed.bookstorecmp.data.data_source.remote.response.BookDetailsData
import org.muhammadsayed.bookstorecmp.data.mappers.fromEntityList
import org.muhammadsayed.bookstorecmp.domain.DataState
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel
import org.muhammadsayed.bookstorecmp.domain.model.BookSearchDomainModel
import org.muhammadsayed.bookstorecmp.domain.repository.AppRepository

class FakeAppRepository(val fakeDao: FakeDao) : AppRepository {

    val bookDetails = BookDetailsData(
        title = "Sample Title",
        subtitle = "Sample Subtitle",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        subjects = listOf("Fiction", "Novel", "Literature"),
        key = "sample_key"
    )


    val book = BookDomainModel(
        id = "12345",
        title = "Sample Book Title",
        subtitle = "Sample Subtitle",
        type = "Fiction",
        price = "24",
        image = "http://example.com/sample_image.jpg",
        author = "John Doe"
    )
    var list = mutableListOf(book)

    override suspend fun addBook(book: BookDomainModel) {
        fakeDao.saveBook(book)
    }

    override suspend fun deleteBook(book: BookDomainModel) {
        fakeDao.deleteBook(book.id.toInt())
    }

    override suspend fun getBooks(): Flow<List<BookDomainModel>> {
        return fakeDao.getAllBooks().map {
            it.fromEntityList()
        }
    }

    override fun getSearchResults(query: String): Flow<DataState<List<BookSearchDomainModel>>> {
        return flowOf(DataState.Success(listOf()))
    }

    override suspend fun getCurrentlyReadingBooks(): Flow<DataState<List<BookDomainModel>>> {
        return flowOf(DataState.Success(list))
    }

    override suspend fun getAlreadyReadBooks(): Flow<DataState<List<BookDomainModel>>> {
        return flowOf(DataState.Success(list))
    }

    override suspend fun getBookDetails(key: String): Flow<DataState<BookDetailsData>> {
        return flowOf(DataState.Success(bookDetails))
    }
}