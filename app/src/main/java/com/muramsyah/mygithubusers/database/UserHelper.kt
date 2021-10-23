package com.muramsyah.mygithubusers.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns._ID
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.USERNAME
import java.sql.SQLException
import kotlin.jvm.Throws

class UserHelper(context: Context) {

    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE: UserHelper? = null
        fun getInstance(context: Context): UserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserHelper(context)
            }

    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteByUsername(username: String?): Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$username'", null)
    }

    fun checkState(username: String?): Int {
        var selection = USERNAME + " LIKE ?"
        var selectionArgs = arrayOf(username)
        val cursor = database.query(
            DATABASE_TABLE,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        return if (cursor.count > 0) 1 else 0
    }
}