package org.gdg.fraud_cmp_app.data.data_source.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FraudDetectionRequest(
    val message: String,
)


