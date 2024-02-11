package org.muhammadsayed.bookstorecmp

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.muhammadsayed.bookstorecmp.shared.data.cache.sqldelight.AppDatabase


actual class TestSqlDriverFactory {
    actual fun create(): SqlDriver {
        val driver = JdbcSqliteDriver(
            url = JdbcSqliteDriver.IN_MEMORY,

            )
        AppDatabase.Schema.create(driver)
        return driver
    }
}
