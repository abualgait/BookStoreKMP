package org.gdg.fraud_cmp_app.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class SmsDomainModel(
    val id: String,
    val senderPhoneNumber:String?,
    val content: String,
    val isCipher:Boolean,
    val subtitle: String,
    val type: String,
)
