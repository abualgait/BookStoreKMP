package org.gdg.fraud_cmp_app.domain.use_case


import org.gdg.fraud_cmp_app.domain.repository.AppRepository

class GetCartItems(
    private val repository: AppRepository
) {

//    suspend operator fun invoke(): Flow<List<BookDomainModel>> {
//        return repository.getBooks()
//    }
}