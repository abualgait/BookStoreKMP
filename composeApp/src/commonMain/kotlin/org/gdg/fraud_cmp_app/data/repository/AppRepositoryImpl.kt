package org.gdg.fraud_cmp_app.data.repository


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.gdg.fraud_cmp_app.data.data_source.remote.request.FraudDetectionRequest
import org.gdg.fraud_cmp_app.data.data_source.remote.response.FraudDetectionDTO
import org.gdg.fraud_cmp_app.data.mappers.mapToDomainModel
import org.gdg.fraud_cmp_app.domain.DataState
import org.gdg.fraud_cmp_app.domain.model.SmsSearchDomainModel
import org.gdg.fraud_cmp_app.domain.repository.AppRepository

class AppRepositoryImpl(
    private val httpClient: HttpClient,
) : AppRepository {

    override suspend fun checkSms(smsMessage: String): Flow<DataState<SmsSearchDomainModel>> =
        flow {
            try {
                emit(DataState.Loading)
                val response = httpClient.post("send-massage") {
                    contentType(ContentType.Application.Json) // Set JSON Content-Type
                    setBody(FraudDetectionRequest(message = smsMessage)) // Set body correctly
                }
                    .body<FraudDetectionDTO>().mapToDomainModel()
                emit(DataState.Success(response))
            } catch (e: Exception) {
                emit(DataState.Error(e.message ?: "Unknown error"))
            }
        }
}
