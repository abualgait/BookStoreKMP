package org.gdg.fraud_cmp_app.presentation.frauddetection

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mapToUIModel
import org.gdg.fraud_cmp_app.domain.DataState
import org.gdg.fraud_cmp_app.domain.use_case.CheckSms
import org.koin.core.component.KoinComponent

class FraudDetectionViewModel constructor(val checkSms: CheckSms) : KoinComponent {

    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    private val _state = MutableStateFlow<FraudDetectionScreenState>(FraudDetectionScreenState())
    val state: StateFlow<FraudDetectionScreenState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = FraudDetectionScreenState(),
    )

    fun onEvent(event: FraudDetectionScreenEvents) {
        when (event) {
            is FraudDetectionScreenEvents.GetSMSMessageFeedback -> {
                viewModelScope.launch {
                    getSMSFeedback(event.message)
                }
            }
        }

    }


    private suspend fun getSMSFeedback(message: String) {
        checkSms.invoke(message)
            .onEach { data ->
                withContext(Dispatchers.Main) {
                    when (data) {
                        is DataState.Error -> {
                            _state.update {
                                it.copy(
                                    loading = false,
                                    error = data.error,
                                )
                            }
                        }

                        DataState.Loading -> {
                            _state.update {
                                it.copy(
                                    loading = true,
                                )
                            }
                        }

                        is DataState.Success -> {
                            _state.update {
                                it.copy(
                                    loading = false,
                                    response = data.data.mapToUIModel(),
                                )
                            }
                        }
                    }

                }
            }.launchIn(viewModelScope)

    }


}
