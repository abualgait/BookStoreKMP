package org.gdg.fraud_cmp_app.presentation.frauddetection

sealed class FraudDetectionScreenEvents {
    data class GetSMSMessageFeedback(val message: String) : FraudDetectionScreenEvents()
}

