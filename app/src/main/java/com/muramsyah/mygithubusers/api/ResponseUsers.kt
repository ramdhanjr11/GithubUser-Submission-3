package com.muramsyah.mygithubusers.api

import com.squareup.moshi.Json

data class ResponseUsers(
    @Json(name = "items")
    val items: List<Items>
)

data class Items(
    @Json(name = "login")
    val username: String,
    @Json(name = "avatar_url")
    val avatar: String,
    @Json(name = "url")
    val url: String
)
