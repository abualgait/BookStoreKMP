package org.gdg.fraud_cmp_app.domain.use_case


import kotlinx.coroutines.flow.Flow
import org.gdg.fraud_cmp_app.data.data_source.remote.response.BookDetailsData
import org.gdg.fraud_cmp_app.domain.DataState
import org.gdg.fraud_cmp_app.domain.repository.AppRepository

class GetBookDetails(
    private val repository: AppRepository
) {

//    suspend operator fun invoke(bookId:String): Flow<DataState<BookDetailsData>> {
//        return repository.getBookDetails(key = bookId)
//    }
}