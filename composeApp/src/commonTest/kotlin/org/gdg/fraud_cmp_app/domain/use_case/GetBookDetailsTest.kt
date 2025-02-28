package org.gdg.fraud_cmp_app.domain.use_case

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.gdg.fraud_cmp_app.data.data_source.remote.response.BookDetailsData
import org.gdg.fraud_cmp_app.domain.DataState
import org.gdg.fraud_cmp_app.testdoubles.FakeAppRepository
import org.gdg.fraud_cmp_app.testdoubles.FakeDao
import kotlin.test.Test
import kotlin.test.assertEquals

class GetBookDetailsTest {
    private val fakeDao = FakeDao()
    private val fakeRepository = FakeAppRepository(fakeDao)

    @Test
    fun `should getCurrentlyReadingBooks`() = runTest {

        val bookDetails = BookDetailsData(
            title = "Sample Title",
            subtitle = "Sample Subtitle",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            subjects = listOf("Fiction", "Novel", "Literature"),
            key = "sample_key"
        )
        val result = fakeRepository.getBookDetails("sample_key")


        result.test {
            val items = awaitItem()
            assertEquals(items, DataState.Success(bookDetails))
            awaitComplete()
        }

    }

}