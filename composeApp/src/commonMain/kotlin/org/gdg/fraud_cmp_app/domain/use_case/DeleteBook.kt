package org.gdg.fraud_cmp_app.domain.use_case


import org.gdg.fraud_cmp_app.domain.repository.AppRepository

class DeleteBook(
    private val repository: AppRepository
) {

//    suspend operator fun invoke(book:BookDomainModel)  {
//        return repository.deleteBook(book)
//    }
}