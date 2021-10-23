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

class FollowersModel : ViewModel() {

    val listFollowers = MutableLiveData<ArrayList<GithubUsers>>()

    fun setUserFollowers(username: String?) {
        GithubClient.apiRetrofit
            .getFollowersUser(username!!)
            .enqueue(object : Callback<ArrayList<GithubUsers>> {
                override fun onResponse(
                    call: Call<ArrayList<GithubUsers>>,
                    response: Response<ArrayList<GithubUsers>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<GithubUsers>>, t: Throwable) {
                    Log.d("error: ", "gagal mengambil data followers : $t")
                }

            })

    }

    fun getUserFollowers() : LiveData<ArrayList<GithubUsers>> {
        return listFollowers
    }
}