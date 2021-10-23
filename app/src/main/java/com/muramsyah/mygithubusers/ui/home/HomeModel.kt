package com.muramsyah.mygithubusers.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.muramsyah.mygithubusers.api.ResponseUsers
import com.muramsyah.mygithubusers.model.GithubUsers
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.msebera.android.httpclient.Header

class HomeModel : ViewModel() {

    val listGithubUsers = MutableLiveData<ArrayList<GithubUsers>>()

    val apiKeyAuth = "cd64c17d1ed4aa9d1a2bd48793e9a9ca0e025a8d"

    fun setGithubUser(query: String?) {
        val listData = ArrayList<GithubUsers>()

        val url_user = "https://api.github.com/search/users?q=$query"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKeyAuth")
        client.addHeader("User-Agent", "request")
        client.get(url_user, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?)
            {
                val result = String(responseBody!!)
                try {
                    // Parsing json
                    val moshi = Moshi.Builder()
                        .addLast(KotlinJsonAdapterFactory())
                        .build()

                    val jsonAdapter = moshi.adapter(ResponseUsers::class.java)
                    val response = jsonAdapter.fromJson(result)

                    for (i in 0 until response!!.items.size) {
                        val githubUsers = GithubUsers()
                        val username = response.items[i].username
                        val avatar = response.items[i].avatar
                        val url = response.items[i].url
                        githubUsers.login = username
                        githubUsers.avatar_url = avatar
                        githubUsers.url = url
                        listData.add(githubUsers)
                    }

                    listGithubUsers.postValue(listData)
                } catch (e: Exception) {
                    Log.d("error: ", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d("error: ", errorMessage)
            }

        })
    }

    fun getGithubUsers() : LiveData<ArrayList<GithubUsers>> {
        return listGithubUsers
    }
}