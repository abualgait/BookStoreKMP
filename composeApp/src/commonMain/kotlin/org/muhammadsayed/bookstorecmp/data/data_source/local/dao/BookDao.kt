package org.muhammadsayed.bookstorecmp.data.data_source.local.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.muhammadsayed.bookstorecmp.data.datasource.local.BookEntity
import org.muhammadsayed.bookstorecmp.domain.model.BookDomainModel
import org.muhammadsayed.bookstorecmp.shared.data.cache.sqldelight.AppDatabase
import org.muhammadsayed.bookstorecmp.utils.DatabaseDriverFactory

interface BookDao {
    fun saveBook(bookDomainModel: BookDomainModel)
    fun getAllBooks(): Flow<List<BookEntity>>
    fun getBook(id: Int): BookEntity
    fun deleteBook(id: Int)
    fun deleteAllBooks()
}

class BookDaoImpl(private val databaseDriverFactory: DatabaseDriverFactory) : BookDao {

    private val appDatabase = AppDatabase(driver = databaseDriverFactory.createDriver())
    private val dbQuery = appDatabase.appDatabaseQueries

    override fun saveBook(bookDomainModel: BookDomainModel) {
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


    override fun getAllBooks() = dbQuery.getAllBooks().asFlow().mapToList(Dispatchers.IO)

    override fun getBook(id: Int) = dbQuery.getBook(id = id.toLong()).executeAsOne()

    override fun deleteBook(id: Int) = dbQuery.deleteBook(id = id.toLong())

    override fun deleteAllBooks() = dbQuery.deleteAllBooks()
}
