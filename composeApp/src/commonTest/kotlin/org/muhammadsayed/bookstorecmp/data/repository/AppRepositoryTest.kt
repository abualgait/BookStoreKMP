package org.muhammadsayed.bookstorecmp.data.repository

import app.cash.turbine.test
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondOk
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.test.runTest
import org.muhammadsayed.bookstorecmp.data.data_source.remote.HttpClientFactory
import org.muhammadsayed.bookstorecmp.data.data_source.remote.response.BookDTO
import org.muhammadsayed.bookstorecmp.data.data_source.remote.response.BookDetailsData
import org.muhammadsayed.bookstorecmp.data.data_source.remote.response.WorkDTO
import org.muhammadsayed.bookstorecmp.data.mappers.fromDTOList
import org.muhammadsayed.bookstorecmp.domain.DataState
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel
import org.muhammadsayed.bookstorecmp.testdoubles.FakeDao
import kotlin.test.Test
import kotlin.test.assertEquals


class AppRepositoryTest {

    private val fakeDao = FakeDao()
    private val defaultHeaders = headersOf(
        HttpHeaders.ContentType, ContentType.Application.Json.toString()
    )


    @Test
    fun `getCurrentlyReadingBooks should return success response`() = runTest {
        val mockEngine = MockEngine.create {
            addHandler { request ->
                when {
                    request.url.encodedPath.contains("people/mekBot/books/currently-reading.json") -> {
                        respond(content = CURRENTLY_READING_LOG_ENTRIES, headers = defaultHeaders)
                    }

                    else -> throw IOException("")
                }
            }
        }

        val sut = AppRepositoryImpl(fakeDao, HttpClientFactory.makeClientTest(mockEngine, false))

        val result = sut.getCurrentlyReadingBooks()

        result.test {
            val loading = awaitItem()
            assertEquals(loading, DataState.Loading)

            val success = awaitItem()
            assertEquals(
                success, DataState.Success(
                    listOf(
                        BookDTO(
                            work = WorkDTO(
                                title = "Sample Book Title",
                                key = "sample_key",
                                authorNames = listOf("Author One", "Author Two"),
                                firstPublishYear = "2020",
                                coverEditionKey = "cover_key"
                            ),
                            loggedDate = "2024-02-10"
                        ),
                        BookDTO(
                            work = WorkDTO(
                                title = "Another Book Title",
                                key = "another_key",
                                authorNames = listOf("Author Three"),
                                firstPublishYear = "2018",
                                coverEditionKey = "another_cover_key"
                            ),
                            loggedDate = "2024-02-09"
                        )
                    ).fromDTOList()
                )
            )
            awaitComplete()

        }
    }

    @Test
    fun `getAlreadyReadBooks should return success response`() = runTest {
        val mockEngine = MockEngine.create {
            addHandler { request ->
                when {
                    request.url.encodedPath.contains("people/mekBot/books/already-read.json") -> {
                        respond(content = ALREADY_READ_LOG_ENTRIES, headers = defaultHeaders)
                    }

                    else -> throw IOException("")
                }
            }
        }

        val sut = AppRepositoryImpl(fakeDao, HttpClientFactory.makeClientTest(mockEngine, false))

        val result = sut.getAlreadyReadBooks()

        result.test {
            val loading = awaitItem()
            assertEquals(loading, DataState.Loading)

            val success = awaitItem()
            assertEquals(
                success, DataState.Success(
                    listOf(
                        BookDTO(
                            work = WorkDTO(
                                title = "Sample Book Title",
                                key = "sample_key",
                                authorNames = listOf("Author One", "Author Two"),
                                firstPublishYear = "2020",
                                coverEditionKey = "cover_key"
                            ),
                            loggedDate = "2024-02-10"
                        ),
                        BookDTO(
                            work = WorkDTO(
                                title = "Another Book Title",
                                key = "another_key",
                                authorNames = listOf("Author Three"),
                                firstPublishYear = "2018",
                                coverEditionKey = "another_cover_key"
                            ),
                            loggedDate = "2024-02-09"
                        )
                    ).fromDTOList()
                )
            )
            awaitComplete()

        }
    }


    @Test
    fun `getBookDetails should return success response`() = runTest {
        val mockEngine = MockEngine.create {
            addHandler { request ->
                when {
                    request.url.encodedPath.contains("works/KEY.json") -> {
                        respond(content = BOOK_DETAILS, headers = defaultHeaders)
                    }

                    else -> throw IOException("")
                }
            }
        }

        val sut = AppRepositoryImpl(fakeDao, HttpClientFactory.makeClientTest(mockEngine, false))

        val result = sut.getBookDetails("KEY")

        result.test {
            val loading = awaitItem()
            assertEquals(loading, DataState.Loading)

            val success = awaitItem()
            assertEquals(
                success, DataState.Success(
                    BookDetailsData(
                        title = "Sample Title",
                        subtitle = "Sample Subtitle",
                        description = "Sample Description",
                        subjects = listOf("Subject1", "Subject2", "Subject3"),
                        key = "sample_key"
                    )
                )
            )
            awaitComplete()

        }
    }


    @Test
    fun `getBooks should return success response`() = runTest {
        val mockEngine = MockEngine.create {
            addHandler { respondOk() }
        }
        val book = BookDomainModel(
            id = "12345",
            title = "Sample Book Title",
            subtitle = "Sample Subtitle",
            type = "Fiction",
            price = "24",
            image = "http://example.com/sample_image.jpg",
            author = "John Doe"
        )
        fakeDao.saveBook(book)
        val sut = AppRepositoryImpl(fakeDao, HttpClientFactory.makeClientTest(mockEngine, false))

        sut.addBook(book)
        val result = sut.getBooks()

        result.test {
            val items = awaitItem()
            assertEquals(items, listOf(book))
        }
    }


    @Test
    fun `deleteBook By Id should return success response`() = runTest {
        val mockEngine = MockEngine.create {
            addHandler { respondOk() }
        }

        val book = BookDomainModel(
            id = "12345",
            title = "Sample Book Title",
            subtitle = "Sample Subtitle",
            type = "Fiction",
            price = "24",
            image = "http://example.com/sample_image.jpg",
            author = "John Doe"
        )
        fakeDao.saveBook(book)

        val sut = AppRepositoryImpl(fakeDao, HttpClientFactory.makeClientTest(mockEngine, false))

        sut.deleteBook(book)
        val result = sut.getBooks()

        result.test {
            val items = awaitItem()
            assertEquals(items, emptyList())
        }
    }


    private companion object {

        //language=JSON
        val CURRENTLY_READING_LOG_ENTRIES = """
             {
               "reading_log_entries": [
                 {
                   "work": {
                     "title": "Sample Book Title",
                     "key": "sample_key",
                     "author_names": ["Author One", "Author Two"],
                     "first_publish_year": "2020",
                     "cover_edition_key": "cover_key"
                   },
                   "logged_date": "2024-02-10"
                 },
                 {
                   "work": {
                     "title": "Another Book Title",
                     "key": "another_key",
                     "author_names": ["Author Three"],
                     "first_publish_year": "2018",
                     "cover_edition_key": "another_cover_key"
                   },
                   "logged_date": "2024-02-09"
                 }
               ]
             }
        """.trimIndent()

        //language=JSON
        val ALREADY_READ_LOG_ENTRIES = """
             {
               "reading_log_entries": [
                 {
                   "work": {
                     "title": "Sample Book Title",
                     "key": "sample_key",
                     "author_names": ["Author One", "Author Two"],
                     "first_publish_year": "2020",
                     "cover_edition_key": "cover_key"
                   },
                   "logged_date": "2024-02-10"
                 },
                 {
                   "work": {
                     "title": "Another Book Title",
                     "key": "another_key",
                     "author_names": ["Author Three"],
                     "first_publish_year": "2018",
                     "cover_edition_key": "another_cover_key"
                   },
                   "logged_date": "2024-02-09"
                 }
               ]
             }
        """.trimIndent()

        val BOOK_DETAILS = """
              {
              "title": "Sample Title",
              "subtitle": "Sample Subtitle",
              "description": "Sample Description",
              "subjects": ["Subject1", "Subject2", "Subject3"],
              "key": "sample_key"
            }
        """.trimIndent()

    }
}
