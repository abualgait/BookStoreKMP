package org.gdg.fraud_cmp_app.utils

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.gdg.fraud_cmp_app.shared.data.cache.sqldelight.AppDatabase


actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, "Books.db")
    }
}
