package com.muramsyah.mygithubusers.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muramsyah.mygithubusers.api.GithubClient
import com.muramsyah.mygithubusers.model.GithubUsers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingModel : ViewModel() {

    val listFollowing = MutableLiveData<ArrayList<GithubUsers>>()

    fun setUserFollowing(username: String?) {
        GithubClient.apiRetrofit
            .getFollowingUser(username!!)
            .enqueue(object : Callback<ArrayList<GithubUsers>> {
                override fun onResponse(
                    call: Call<ArrayList<GithubUsers>>,
                    response: Response<ArrayList<GithubUsers>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<GithubUsers>>, t: Throwable) {
                    Log.d("error: ", "gagal mengambil data following : $t")
                }

            })

    }

    fun getUserFollowing() : LiveData<ArrayList<GithubUsers>> {
        return listFollowing
    }
}