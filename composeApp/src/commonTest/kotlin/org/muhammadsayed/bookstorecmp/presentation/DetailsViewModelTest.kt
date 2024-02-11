package org.muhammadsayed.bookstorecmp.presentation

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.muhammadsayed.bookstorecmp.domain.use_case.AddBook
import org.muhammadsayed.bookstorecmp.domain.use_case.GetBookDetails
import org.muhammadsayed.bookstorecmp.presentation.details.DetailsScreenEvents
import org.muhammadsayed.bookstorecmp.presentation.details.DetailsScreenState
import org.muhammadsayed.bookstorecmp.presentation.details.DetailsViewModel
import org.muhammadsayed.bookstorecmp.testdoubles.FakeAppRepository
import org.muhammadsayed.bookstorecmp.testdoubles.FakeDao
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {
    private val fakeDao = FakeDao()
    private val fakeRepository = FakeAppRepository(fakeDao)
    private val getBookDetails = GetBookDetails(fakeRepository)
    private val addBook = AddBook(fakeRepository)

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `should emit DetailsScreenState state when call event GetBookDetails`() = runTest {

        val sut = createViewModel()
        sut.onEvent(DetailsScreenEvents.GetBookDetails("sample_key"))
        sut.state.test {
            assertEquals(DetailsScreenState.Loading, awaitItem())
            assertEquals(
                DetailsScreenState.Success.BookDetails(fakeRepository.bookDetails),
                awaitItem()
            )
        }
    }

    @Test
    fun `should addBook`() = runTest {

        val sut = createViewModel()
        sut.onEvent(DetailsScreenEvents.AddBook(fakeRepository.book))

        fakeRepository.getBooks().test {
            awaitEvent()
            assertEquals(listOf(fakeRepository.book), awaitItem())

        }
    }

    private fun createViewModel(): DetailsViewModel {
        return DetailsViewModel(
            getBookDetails = getBookDetails,
            addBook = addBook
        )
    }

}