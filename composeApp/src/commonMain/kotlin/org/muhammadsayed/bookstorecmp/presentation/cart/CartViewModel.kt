package org.muhammadsayed.bookstorecmp.presentation.cart

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
import org.muhammadsayed.bookstorecmp.domain.use_case.DeleteBook
import org.muhammadsayed.bookstorecmp.domain.use_case.GetCartItems

class CartViewModel constructor(
    private val getCartItems: GetCartItems,
    private val addBook: AddBook,
    private val deleteBook: DeleteBook,
) : KoinComponent {

    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    private val _state = MutableStateFlow<CartScreenState>(CartScreenState())
    val state: StateFlow<CartScreenState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CartScreenState(),
    )

    fun onEvent(event: CartScreenEvents) {
        when (event) {
            CartScreenEvents.LoadCartItems -> {
                viewModelScope.launch {
                    getCartItems()
                }
            }

            is CartScreenEvents.UpdateBookQty -> {
                viewModelScope.launch { addBook.invoke(event.book) }
            }

            is CartScreenEvents.DeleteBook -> {
                viewModelScope.launch { deleteBook.invoke(event.book) }
            }
        }

    }


    private suspend fun getCartItems() {
        getCartItems.invoke()
            .onEach { data ->
                withContext(Dispatchers.Main) {
                    _state.update {
                        var subTotal = 0L
                        it.cartItems?.forEach { book ->
                            subTotal += book.qty?.times(book.price.toInt()) ?: 0L
                        }

                        it.copy(
                            cartItems = data,
                            subTotal = subTotal,
                            total = it.subTotal?.plus(10L)
                        )
                    }
                }
            }.launchIn(viewModelScope)

    }


}
