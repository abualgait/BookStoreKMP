package org.gdg.fraud_cmp_app.presentation.frauddetection

import org.gdg.fraud_cmp_app.domain.model.SmsSearchDomainModel

data class FraudDetectionScreenState(
    val loading: Boolean = false,
    val error: String = "",
    val response: FraudDetectionUIModel = FraudDetectionUIModel("",null),
)

