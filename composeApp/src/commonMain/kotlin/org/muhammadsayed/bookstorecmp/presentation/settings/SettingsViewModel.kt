package org.muhammadsayed.bookstorecmp.presentation.settings

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
import org.koin.core.component.KoinComponent
import org.muhammadsayed.bookstorecmp.domain.repository.SettingsRepository

class SettingsViewModel(private val settingsRepository: SettingsRepository) :
    KoinComponent {

    private val KEY_SHOW_GET_STARTED = "show-get-started"

    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    private val _state = MutableStateFlow<SettingsState>(SettingsState())
    val state: StateFlow<SettingsState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SettingsState(),
    )

    init {
        viewModelScope.launch {
            getIsGetStartedShown()
        }
    }

    fun savePreferenceGetStarted() {
        viewModelScope.launch {
            settingsRepository.savePreferenceGetStarted(KEY_SHOW_GET_STARTED, false)
        }
    }

    private suspend fun getIsGetStartedShown() {

        settingsRepository.getIsGetStartedShown(KEY_SHOW_GET_STARTED).onEach { showGetStarted ->
            _state.update {
                it.copy(showGetStarted = showGetStarted)
            }
        }.launchIn(viewModelScope)

    }
}
