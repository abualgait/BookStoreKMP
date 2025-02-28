package org.gdg.fraud_cmp_app.testdoubles

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.gdg.fraud_cmp_app.data.data_source.remote.response.FraudDetectionDTO
import org.gdg.fraud_cmp_app.data.mappers.fromEntityList
import org.gdg.fraud_cmp_app.domain.DataState
import org.gdg.fraud_cmp_app.domain.model.SmsSearchDomainModel
import org.gdg.fraud_cmp_app.domain.repository.AppRepository

class FakeAppRepository(val fakeDao: FakeDao) : AppRepository {

    val bookDetails = FraudDetectionDTO(
        status = "Sample Title",
        feedback = "Sample Subtitle",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        subjects = listOf("Fiction", "Novel", "Literature"),
        key = "sample_key"
    )


    val book = SmsDomainModel(
        id = "12345",
        title = "Sample Book Title",
        subtitle = "Sample Subtitle",
        type = "Fiction",
        price = "24",
        image = "http://example.com/sample_image.jpg",
        author = "John Doe"
    )
    var list = mutableListOf(book)

    override suspend fun addBook(book: SmsDomainModel) {
        fakeDao.saveBook(book)
    }

    override suspend fun deleteBook(book: SmsDomainModel) {
        fakeDao.deleteBook(book.id.toInt())
    }

    override suspend fun getBooks(): Flow<List<SmsDomainModel>> {
        return fakeDao.getAllBooks().map {
            it.fromEntityList()
        }
    }

    override fun getSearchResults(query: String): Flow<DataState<List<SmsSearchDomainModel>>> {
        return flowOf(DataState.Success(listOf()))
    }

    override suspend fun getCurrentlyReadingBooks(): Flow<DataState<List<SmsDomainModel>>> {
        return flowOf(DataState.Success(list))
    }

    override suspend fun getAlreadyReadBooks(): Flow<DataState<List<SmsDomainModel>>> {
        return flowOf(DataState.Success(list))
    }

    override suspend fun getBookDetails(key: String): Flow<DataState<FraudDetectionDTO>> {
        return flowOf(DataState.Success(bookDetails))
    }
}