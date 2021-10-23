package com.muramsyah.mygithubusers.api

import android.os.Parcelable
import com.muramsyah.mygithubusers.model.GithubUsers
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseItems(
    var items: ArrayList<GithubUsers>
) : Parcelable
