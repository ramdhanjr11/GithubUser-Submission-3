package com.muramsyah.mygithubusers.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.muramsyah.mygithubusers.database.DatabaseContract.AUTHORITHY
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.muramsyah.mygithubusers.database.UserHelper

class MyGithubUsersProvider : ContentProvider() {

    companion object {
        private const val USERS = 1
        private const val USERS_DELETE = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var userHelper: UserHelper
    }

    init {
        sUriMatcher.addURI(AUTHORITHY, TABLE_NAME, USERS)
        sUriMatcher.addURI(AUTHORITHY, "$TABLE_NAME/*", USERS_DELETE)
    }

    override fun onCreate(): Boolean {
        userHelper = UserHelper.getInstance(context as Context)
        userHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            USERS -> userHelper.queryAll()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (USERS) {
            sUriMatcher.match(uri) -> userHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val delete: Int = when (USERS_DELETE) {
            sUriMatcher.match(uri) -> userHelper.deleteByUsername(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return delete
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}