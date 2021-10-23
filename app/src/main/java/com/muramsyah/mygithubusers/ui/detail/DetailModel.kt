package com.muramsyah.mygithubusers.ui.detail

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.muramsyah.mygithubusers.api.GithubClient
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.URL
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.USERNAME
import com.muramsyah.mygithubusers.database.UserHelper
import com.muramsyah.mygithubusers.model.DetailUser
import com.muramsyah.mygithubusers.model.GithubUsers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailModel(application: Application) : AndroidViewModel(application) {

    val userDetail = MutableLiveData<DetailUser>()

    private lateinit var userHelper: UserHelper

    private lateinit var context: Context

    init {
        userHelper = UserHelper(application)
        context = application
    }

    fun setGithubUserRetrofit(username: String?) {
        GithubClient.apiRetrofit
            .getDetailUserData(username = username!!)
            .enqueue(object : Callback<DetailUser> {
                override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                    if (response.isSuccessful) {
                        userDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                    Log.d("error: ", "message : $t")
                }

            })
    }

    fun getGithubUserDetail(): LiveData<DetailUser> {
        return userDetail
    }

    fun setFavoriteUser(data: GithubUsers) {
        CoroutineScope(Dispatchers.IO).launch {
            userHelper.open()

            val contentValues = ContentValues().apply {
                put(USERNAME, data.login)
                put(AVATAR_URL, data.avatar_url)
                put(URL, data.url)
            }

            context.contentResolver.insert(CONTENT_URI, contentValues)

            userHelper.close()
        }
    }

    fun checkIsFavoriteUser(username: String?): Int {
        userHelper.open()
        val result = userHelper.checkState(username)
        userHelper.close()
        return result
    }

    fun deleteByUsername(username: String?) {
        userHelper.open()
        val uriWithUsername = Uri.parse(CONTENT_URI.toString() + "/" + username)
        context.contentResolver.delete(uriWithUsername, null, null)
        userHelper.close()
    }
}