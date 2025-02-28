package org.gdg.fraud_cmp_app.domain.repository

import org.gdg.fraud_cmp_app.domain.model.SmsDomainModel
import org.gdg.fraud_cmp_app.domain.model.SmsSearchDomainModel

interface AppRepository {
//    suspend fun addBook(book: BookDomainModel)
//    suspend fun deleteBook(book: BookDomainModel)
//    suspend fun getBooks(): Flow<List<BookDomainModel>>
//    fun getSearchResults(query: String): Flow<DataState<List<BookSearchDomainModel>>>
//    suspend fun getCurrentlyReadingBooks(): Flow<DataState<List<BookDomainModel>>>
//    suspend fun getAlreadyReadBooks(): Flow<DataState<List<BookDomainModel>>>
//    suspend fun getBookDetails(key:String): Flow<DataState<BookDetailsData>>

    suspend fun checkSms(sms:SmsDomainModel):Boolean
    suspend fun searchByPhoneNumber(phoneNumber:SmsSearchDomainModel):SmsDomainModel

}