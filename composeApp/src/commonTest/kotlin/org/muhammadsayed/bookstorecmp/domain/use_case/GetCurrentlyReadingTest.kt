package org.muhammadsayed.bookstorecmp.domain.use_case

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.muhammadsayed.bookstorecmp.domain.DataState
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel
import org.muhammadsayed.bookstorecmp.testdoubles.FakeAppRepository
import org.muhammadsayed.bookstorecmp.testdoubles.FakeDao
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCurrentlyReadingTest {
    private val fakeDao = FakeDao()
    private val fakeRepository = FakeAppRepository(fakeDao)

    @Test
    fun `should getCurrentlyReadingBooks`() = runTest {
        val book = BookDomainModel(
            id = "12345",
            title = "Sample Book Title",
            subtitle = "Sample Subtitle",
            type = "Fiction",
            price = "24",
            image = "http://example.com/sample_image.jpg",
            author = "John Doe"
        )
        val result = fakeRepository.getCurrentlyReadingBooks()


        result.test {
            val items = awaitItem()
            assertEquals(items, DataState.Success(listOf(book)))
            awaitComplete()
        }

    }

}