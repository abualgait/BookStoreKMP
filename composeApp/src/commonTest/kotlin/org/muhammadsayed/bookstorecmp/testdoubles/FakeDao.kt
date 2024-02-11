package org.muhammadsayed.bookstorecmp.testdoubles

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.muhammadsayed.bookstorecmp.TestSqlDriverFactory
import org.muhammadsayed.bookstorecmp.data.data_source.local.dao.BookDao
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel
import org.muhammadsayed.bookstorecmp.shared.data.cache.sqldelight.AppDatabase

class FakeDao : BookDao {
    private val sqlDriver = TestSqlDriverFactory().create()
    private val database = AppDatabase(sqlDriver)
    private val dbQuery = database.appDatabaseQueries

    override fun saveBook(bookDomainModel: BookDomainModel) {
        dbQuery.transaction {
            dbQuery.insertOrUpdateBook(
                id = bookDomainModel.id.toLong(),
                title = bookDomainModel.title,
                subtitle = bookDomainModel.subtitle,
                description = "",
                author = bookDomainModel.author,
                type = bookDomainModel.type,
                qty = bookDomainModel.qty,
                price = "24",
                image = bookDomainModel.image,
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    .toString()
            )
        }
    }


    override fun getAllBooks() = dbQuery.getAllBooks().asFlow().mapToList(Dispatchers.Unconfined)

    override fun getBook(id: Int) = dbQuery.getBook(id = id.toLong()).executeAsOne()

    override fun deleteBook(id: Int) = dbQuery.deleteBook(id = id.toLong())

    override fun deleteAllBooks() = dbQuery.deleteAllBooks()

}