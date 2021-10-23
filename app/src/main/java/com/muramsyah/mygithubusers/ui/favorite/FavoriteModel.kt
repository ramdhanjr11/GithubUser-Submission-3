package com.muramsyah.mygithubusers.ui.favorite

import android.app.Application
import android.content.Context
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.muramsyah.mygithubusers.database.DatabaseContract
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.URL
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.USERNAME
import com.muramsyah.mygithubusers.model.GithubUsers

class FavoriteModel(application: Application) : AndroidViewModel(application) {

    val listGithubUsers = MutableLiveData<ArrayList<GithubUsers>>()

    private var context: Context = application

    fun getGithubUsers() : LiveData<ArrayList<GithubUsers>> {
        val cursor = context.contentResolver.query(
            CONTENT_URI,
            null,
            null,
            null,
            null)
        val githubUsers = mappingHelper(cursor)

        listGithubUsers.postValue(githubUsers)

        return listGithubUsers
    }

    fun mappingHelper(cursor: Cursor?) : ArrayList<GithubUsers> {
        var githubUsers = ArrayList<GithubUsers>()
        if (cursor?.count != 0) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    var users = GithubUsers().apply {
                        login = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME))
                        avatar_url = cursor.getString(cursor.getColumnIndexOrThrow(AVATAR_URL))
                        url = cursor.getString(cursor.getColumnIndexOrThrow(URL))
                    }
                    githubUsers.add(users)
                }
            }
        }
        return githubUsers
    }

}