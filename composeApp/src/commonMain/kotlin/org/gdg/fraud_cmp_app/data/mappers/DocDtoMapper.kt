package org.gdg.fraud_cmp_app.data.mappers


import org.gdg.fraud_cmp_app.data.data_source.remote.response.DocDTO
import org.gdg.fraud_cmp_app.domain.model.SmsSearchDomainModel


fun DocDTO.mapToDomainModel(): SmsSearchDomainModel {
    return SmsSearchDomainModel(
        id = key,
        senderPhoneNumber = title,

    )
}



fun List<DocDTO>.fromDTOList(): List<SmsSearchDomainModel> {
    return map { it.mapToDomainModel() }
}










