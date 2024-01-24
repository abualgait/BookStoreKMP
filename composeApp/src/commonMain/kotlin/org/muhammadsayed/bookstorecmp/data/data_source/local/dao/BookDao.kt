package org.muhammadsayed.bookstorecmp.data.data_source.local.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel
import org.muhammadsayed.bookstorecmp.shared.data.cache.sqldelight.AppDatabase
import org.muhammadsayed.bookstorecmp.utils.DatabaseDriverFactory

class BookDao(private val databaseDriverFactory: DatabaseDriverFactory) {

    private val appDatabase = AppDatabase(driver = databaseDriverFactory.createDriver())
    private val dbQuery = appDatabase.appDatabaseQueries

    fun saveBook(bookDomainModel: BookDomainModel) {
        dbQuery.transaction {
            dbQuery.insertOrUpdateBook(
                id = bookDomainModel.id.removePrefix("OL").removeSuffix("W").toLong(),
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

    fun getAllBooks() = dbQuery.getAllBooks().asFlow().mapToList(Dispatchers.IO)

    fun getBook(id: Int) =
        dbQuery.getBook(id = id.toLong()).executeAsOne()

    fun deleteBook(id: Int) = dbQuery.deleteBook(id = id.toLong())

    fun deleteAllBooks() = dbQuery.deleteAllBooks()
}
