package org.gdg.fraud_cmp_app.data.mappers


import org.gdg.fraud_cmp_app.data.data_source.remote.response.FraudDetectionDTO
import org.gdg.fraud_cmp_app.domain.model.SmsSearchDomainModel


fun FraudDetectionDTO.mapToDomainModel(): SmsSearchDomainModel {
    return SmsSearchDomainModel(
        status = status ?: false,
        feedback = feedback ?: "",

    )
}










