package com.androidacademy.angel.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdvertModel(
    var id: Long? = 0L,
    var status: String? = "",
    var title: String? = "",
    var description: String? = "",
    var url: String? = "",
    var nextEvent: NextEvent? = null
) : Parcelable