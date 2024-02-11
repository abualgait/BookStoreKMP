package org.muhammadsayed.bookstorecmp

import app.cash.sqldelight.db.SqlDriver

expect class TestSqlDriverFactory() {
    fun create(): SqlDriver
}
