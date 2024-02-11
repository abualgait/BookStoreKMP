package org.muhammadsayed.bookstorecmp

import app.cash.sqldelight.db.SqlDriver

actual class TestSqlDriverFactory {
    actual fun create(): SqlDriver {
        val configuration = nativeDatabaseConfiguration(inMemory = true)
        return NativeSqliteDriver(configuration)
    }
}
