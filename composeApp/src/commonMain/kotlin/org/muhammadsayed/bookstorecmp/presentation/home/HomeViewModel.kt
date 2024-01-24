package org.muhammadsayed.bookstorecmp.presentation.home

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
import org.koin.core.component.KoinComponent
import org.muhammadsayed.bookstorecmp.domain.use_case.AddBook
import org.muhammadsayed.bookstorecmp.domain.use_case.GetAlreadyRead
import org.muhammadsayed.bookstorecmp.domain.use_case.GetCurrentlyReading

class HomeViewModel constructor(
    private val getCurrentlyReading: GetCurrentlyReading,
    private val getAlreadyRead: GetAlreadyRead,

) :
    KoinComponent {

    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HomeScreenState(),
    )

    private fun onEvent() {
        viewModelScope.launch {
            getCurrentlyReadingData()
            getAlreadyReadData()
        }
    }

    init {
        onEvent()
    }

    private suspend fun getCurrentlyReadingData() {
        getCurrentlyReading()
            .onEach { data ->
                _state.update { it.copy(loading = true) }
                if (data.data != null) {
                    withContext(Dispatchers.Main) {
                        _state.update { it.copy(loading = false) }
                        _state.update { it.copy(currentlyReading = data.data) }
                    }
                }
                if (data.error != null) {
                    _state.update { it.copy(loading = false) }
                    _state.update { it.copy(error = data.error) }
                }
            }.launchIn(viewModelScope)

    }


    private suspend fun getAlreadyReadData() {
        getAlreadyRead()
            .onEach { data ->
                _state.update { it.copy(alreadyReadLoading = true) }
                if (data.data != null) {
                    withContext(Dispatchers.Main) {
                        _state.update { it.copy(alreadyReadLoading = false) }
                        _state.update { it.copy(alreadyRead = data.data) }
                    }
                }
                if (data.error != null) {
                    _state.update { it.copy(alreadyReadLoading = false) }
                    _state.update { it.copy(error = data.error) }
                }


            }.launchIn(viewModelScope)
    }
}
