package org.gdg.fraud_cmp_app.domain.use_case

import org.gdg.fraud_cmp_app.domain.model.SmsDomainModel
import org.gdg.fraud_cmp_app.domain.repository.AppRepository

class CheckSms(
    private val appRepository: AppRepository
) {

    suspend fun check(smsDomainModel: SmsDomainModel):Boolean{
        return appRepository.checkSms(smsDomainModel)
    }

}