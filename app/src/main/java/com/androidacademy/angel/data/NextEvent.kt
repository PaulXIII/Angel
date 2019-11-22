package com.androidacademy.angel.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NextEvent(
    var longitude: Long? = null,
    var latitude: Long? = null,
    var startTime: Long? = null,
    var endTime: Long? = null
) : Parcelable