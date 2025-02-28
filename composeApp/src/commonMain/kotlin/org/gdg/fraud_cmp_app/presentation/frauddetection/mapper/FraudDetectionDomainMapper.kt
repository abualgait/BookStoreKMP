import org.gdg.fraud_cmp_app.data.data_source.remote.response.FraudDetectionDTO
import org.gdg.fraud_cmp_app.domain.model.SmsSearchDomainModel
import org.gdg.fraud_cmp_app.presentation.frauddetection.FraudDetectionUIModel


fun SmsSearchDomainModel.mapToUIModel(): FraudDetectionUIModel {
    return FraudDetectionUIModel(
        feedback = feedback ?: "",
        status = status
    )
}










