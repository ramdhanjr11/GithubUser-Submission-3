package com.muramsyah.mygithubusers.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITHY = "com.muramsyah.mygithubusers"
    const val SCHEME = "content"

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favoriteuser"
            const val USERNAME = "username"
            const val AVATAR_URL = "avatar_url"
            const val URL = "url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITHY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

}