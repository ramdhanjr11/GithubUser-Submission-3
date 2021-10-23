package com.muramsyah.mygithubusers.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reminder(
    var reminder: Boolean = false
) : Parcelable
