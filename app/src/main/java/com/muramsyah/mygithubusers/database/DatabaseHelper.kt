package com.muramsyah.mygithubusers.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.URL
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.USERNAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {

        private const val DATABASE_NAME = "dbuser"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                " ($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $USERNAME TEXT NOT NULL," +
                " $AVATAR_URL TEXT NOT NULL," +
                " $URL TEXT NOT NULL)"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}