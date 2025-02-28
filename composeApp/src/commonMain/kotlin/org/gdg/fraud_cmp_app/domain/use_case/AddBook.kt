package org.gdg.fraud_cmp_app.domain.use_case


import org.gdg.fraud_cmp_app.domain.repository.AppRepository

class AddBook(
    private val repository: AppRepository
) {

//    suspend operator fun invoke(book:BookDomainModel)  {
//        return repository.addBook(book)
//    }
}