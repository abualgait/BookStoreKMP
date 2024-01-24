package org.muhammadsayed.bookstorecmp.presentation.details

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.muhammadsayed.bookstorecmp.domain.use_case.AddBook
import org.muhammadsayed.bookstorecmp.domain.use_case.GetBookDetails

class DetailsViewModel constructor(
    private val getBookDetails: GetBookDetails,
    private val addBook: AddBook,
) :
    KoinComponent {
    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    private val _state = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Loading)
    val state: StateFlow<DetailsScreenState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = DetailsScreenState.Loading,
    )

    fun onEvent(event: DetailsScreenEvents) {

        when (event) {
            is DetailsScreenEvents.AddBook -> {
                viewModelScope.launch { addBook.invoke(event.book) }

            }

            is DetailsScreenEvents.GetBookDetails -> {
                viewModelScope.launch { getCurrentlyReadingData(event.bookId) }
            }
        }

    }


    private suspend fun getCurrentlyReadingData(bookId: String) {
        getBookDetails(bookId)
            .onEach { data ->
                _state.value = DetailsScreenState.Loading
                if (data.data != null) {
                    withContext(Dispatchers.Main) {
                        _state.value = DetailsScreenState.Success.BookDetails(data.data)
                    }
                }
                if (data.error != null) {
                    _state.value = DetailsScreenState.Error(data.error)
                }
            }.launchIn(viewModelScope)

    }
}
