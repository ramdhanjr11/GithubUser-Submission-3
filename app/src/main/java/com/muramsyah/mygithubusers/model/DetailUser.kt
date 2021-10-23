package com.muramsyah.mygithubusers.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUser(
    var login: String? = "",
    var avatar_url: String? = "",
    var url: String? = "",
    var name: String? = "",
    var company: String? = "",
    var location: String? = "",
    var bio: String? = "",
    var followers: Int? = 0,
    var following: Int? = 0,
    var public_repos: Int? = 0
) : Parcelable
