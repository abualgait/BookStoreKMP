package org.gdg.fraud_cmp_app

import app.cash.sqldelight.db.SqlDriver

expect class TestSqlDriverFactory() {
    fun create(): SqlDriver
}
