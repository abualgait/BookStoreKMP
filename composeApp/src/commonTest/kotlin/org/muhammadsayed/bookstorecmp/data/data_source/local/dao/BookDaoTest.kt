package org.muhammadsayed.bookstorecmp.data.data_source.local.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.muhammadsayed.bookstorecmp.TestSqlDriverFactory
import org.muhammadsayed.bookstorecmp.shared.data.cache.sqldelight.AppDatabase
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BookDaoTest {

    private val sqlDriver = TestSqlDriverFactory().create()
    private val database = AppDatabase(sqlDriver)

    @AfterTest
    fun teardown() {
        sqlDriver.close()
    }


    @Test
    fun `should save book`() = runTest {
        with(database.appDatabaseQueries) {
            insertOrUpdateBook(1, "title1", "subTitle", "", "", "", 1L, "5.0", "", "")
            insertOrUpdateBook(2, "title2", "subTitle", "", "", "", 1L, "5.0", "", "")
            insertOrUpdateBook(3, "title3", "subTitle", "", "", "", 1L, "5.0", "", "")
        }
        val result = database.appDatabaseQueries.getAllBooks().asFlow().mapToList(testScheduler)

        result.test {
            val items = awaitItem()
            assertEquals(items.size, 3)
        }
    }

    @Test
    fun `should get book`() = runTest {
        with(database.appDatabaseQueries) {
            insertOrUpdateBook(123, "title", "subTitle", "", "", "", 1L, "5.0", "", "")
        }
        val result = database.appDatabaseQueries.getBook(123).asFlow().mapToOne(testScheduler)

        result.test {
            val item = awaitItem()
            assertEquals(item.title, "title")
        }
    }

    @Test
    fun `should delete book`() = runTest {
        with(database.appDatabaseQueries) {
            insertOrUpdateBook(123, "title", "subTitle", "", "", "", 1L, "5.0", "", "")
        }
        database.appDatabaseQueries.deleteBook(123)
        val result = database.appDatabaseQueries.getAllBooks().asFlow().mapToList(testScheduler)

        result.test {
            val items = awaitItem()
            assertEquals(items.size, 0)
        }

    }

    @Test
    fun `should delete all books`() = runTest {
        with(database.appDatabaseQueries) {
            insertOrUpdateBook(1, "title1", "subTitle", "", "", "", 1L, "5.0", "", "")
            insertOrUpdateBook(2, "title2", "subTitle", "", "", "", 1L, "5.0", "", "")
            insertOrUpdateBook(3, "title3", "subTitle", "", "", "", 1L, "5.0", "", "")
        }
        database.appDatabaseQueries.deleteAllBooks()

        val result = database.appDatabaseQueries.getAllBooks().asFlow().mapToList(testScheduler)

        result.test {
            val items = awaitItem()
            assertEquals(items.size, 0)
        }

    }


}