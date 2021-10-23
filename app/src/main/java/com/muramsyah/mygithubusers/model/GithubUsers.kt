package com.muramsyah.mygithubusers.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUsers(
    var login: String? = "",
    var avatar_url: String? = "",
    var url: String? = ""
) : Parcelable
