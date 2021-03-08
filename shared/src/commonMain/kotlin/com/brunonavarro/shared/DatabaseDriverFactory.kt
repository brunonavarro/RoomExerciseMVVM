package com.brunonavarro.shared

import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.db.SqlPreparedStatement

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}