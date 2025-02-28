package org.gdg.fraud_cmp_app.domain.use_case


import org.gdg.fraud_cmp_app.domain.repository.AppRepository

class GetCurrentlyReading(
    private val repository: AppRepository
) {

//    suspend operator fun invoke(): Flow<DataState<List<BookDomainModel>>> {
//        return repository.getCurrentlyReadingBooks()
//    }
}