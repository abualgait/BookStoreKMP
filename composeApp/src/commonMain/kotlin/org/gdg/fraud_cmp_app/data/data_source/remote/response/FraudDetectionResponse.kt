package org.gdg.fraud_cmp_app.data.data_source.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FraudDetectionDTO(
    @SerialName("status")
    val status: Boolean? = null,
    @SerialName("message")
    val feedback: String? = null,
)


