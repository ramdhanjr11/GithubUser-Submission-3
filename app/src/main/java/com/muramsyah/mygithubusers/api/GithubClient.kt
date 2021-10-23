package com.muramsyah.mygithubusers.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubClient {

    private const val URL = "https://api.github.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiRetrofit = retrofit.create(GithubApi::class.java)
}