package org.gdg.fraud_cmp_app.domain.repository

import kotlinx.coroutines.flow.Flow
import org.gdg.fraud_cmp_app.domain.DataState
import org.gdg.fraud_cmp_app.domain.model.SmsSearchDomainModel

interface AppRepository {

    suspend fun checkSms(smsMessage: String): Flow<DataState<SmsSearchDomainModel>>
}