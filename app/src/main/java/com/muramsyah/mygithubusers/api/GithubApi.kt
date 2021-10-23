package com.muramsyah.mygithubusers.api

import com.muramsyah.mygithubusers.model.DetailUser
import com.muramsyah.mygithubusers.model.GithubUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("search/users")
    @Headers("Authorization: token cd64c17d1ed4aa9d1a2bd48793e9a9ca0e025a8d")
    fun getQueryUser(@Query("q")
        username: String
    ) : Call<ResponseItems>

    @GET("users/{username}")
    @Headers("Authorization: token cd64c17d1ed4aa9d1a2bd48793e9a9ca0e025a8d")
    fun getDetailUserData(
        @Path("username")
        username: String
    ) : Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token cd64c17d1ed4aa9d1a2bd48793e9a9ca0e025a8d")
    fun getFollowersUser(
        @Path("username")
        username: String
    ) : Call<ArrayList<GithubUsers>>

    @GET("users/{username}/following")
    @Headers("Authorization: token cd64c17d1ed4aa9d1a2bd48793e9a9ca0e025a8d")
    fun getFollowingUser(
        @Path("username")
        username: String
    ) : Call<ArrayList<GithubUsers>>
}