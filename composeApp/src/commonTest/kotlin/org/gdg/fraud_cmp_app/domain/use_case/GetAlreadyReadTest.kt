package org.gdg.fraud_cmp_app.domain.use_case

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.gdg.fraud_cmp_app.domain.DataState
import org.gdg.fraud_cmp_app.domain.model.SmsDomainModel
import org.gdg.fraud_cmp_app.testdoubles.FakeAppRepository
import org.gdg.fraud_cmp_app.testdoubles.FakeDao
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAlreadyReadTest {
    private val fakeDao = FakeDao()
    private val fakeRepository = FakeAppRepository(fakeDao)

    @Test
    fun `should GetAlreadyRead Books`() = runTest {
        val book = SmsDomainModel(
            id = "12345",
            title = "Sample Book Title",
            subtitle = "Sample Subtitle",
            type = "Fiction",
            price = "24",
            image = "http://example.com/sample_image.jpg",
            author = "John Doe"
        )
        val result = fakeRepository.getAlreadyReadBooks()


        result.test {
            val items = awaitItem()
            assertEquals(items, DataState.Success(listOf(book)))
            awaitComplete()
        }

    }

}