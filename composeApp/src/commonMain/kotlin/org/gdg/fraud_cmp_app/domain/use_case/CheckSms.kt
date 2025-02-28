package org.gdg.fraud_cmp_app.domain.use_case

import kotlinx.coroutines.flow.Flow
import org.gdg.fraud_cmp_app.domain.DataState
import org.gdg.fraud_cmp_app.domain.model.SmsSearchDomainModel
import org.gdg.fraud_cmp_app.domain.repository.AppRepository

class CheckSms(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke(smsMessage: String): Flow<DataState<SmsSearchDomainModel>> {
        return appRepository.checkSms(smsMessage = smsMessage)
    }

}