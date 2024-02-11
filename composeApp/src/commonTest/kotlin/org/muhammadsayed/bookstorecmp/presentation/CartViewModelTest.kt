package org.muhammadsayed.bookstorecmp.presentation

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.muhammadsayed.bookstorecmp.domain.use_case.AddBook
import org.muhammadsayed.bookstorecmp.domain.use_case.DeleteBook
import org.muhammadsayed.bookstorecmp.domain.use_case.GetCartItems
import org.muhammadsayed.bookstorecmp.presentation.cart.CartScreenEvents
import org.muhammadsayed.bookstorecmp.presentation.cart.CartScreenState
import org.muhammadsayed.bookstorecmp.presentation.cart.CartViewModel
import org.muhammadsayed.bookstorecmp.testdoubles.FakeAppRepository
import org.muhammadsayed.bookstorecmp.testdoubles.FakeDao
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {
    private val fakeDao = FakeDao()
    private val fakeRepository = FakeAppRepository(fakeDao)
    private val addBook = AddBook(fakeRepository)
    private val getCartItems = GetCartItems(fakeRepository)
    private val deleteBook = DeleteBook(fakeRepository)

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `should emit empty state when call event LoadCartItems`() = runTest {

        val sut = createViewModel()

        sut.onEvent(CartScreenEvents.LoadCartItems)

        sut.state.test {
            assertEquals(
                CartScreenState(cartItems = emptyList(), subTotal = 0, total = 0),
                awaitItem()
            )
        }
    }


    /*@Test
    fun `should emit list of books state when call event UpdateBookQty`() = runTest {

        val sut = createViewModel()

        sut.onEvent(CartScreenEvents.UpdateBookQty(fakeRepository.book))
        sut.onEvent(CartScreenEvents.LoadCartItems)

        sut.state.test {
            assertEquals(
                CartScreenState(cartItems = emptyList(), subTotal = 0, total = 0),
                awaitItem()
            )
            assertEquals(
                CartScreenState(cartItems = fakeRepository.list, subTotal = 24, total = 34),
                awaitItem()
            )
            awaitComplete()
        }
    }*/


    private fun createViewModel(): CartViewModel {
        return CartViewModel(
            getCartItems = getCartItems,
            addBook = addBook,
            deleteBook = deleteBook
        )
    }

}