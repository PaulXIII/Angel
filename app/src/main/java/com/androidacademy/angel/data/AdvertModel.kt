package com.androidacademy.angel.data

data class AdvertModel(
    var id: Long? = 0L,
    var status: String? = "",
    var title: String? = "",
    var description: String? = "",
    var url: String? = "",
    var nextEvent: NextEvent? = null
)

data class NextEvent(
    var longitude: Long? = null,
    var latitude: Long? = null,
    var startTime: Long? = null,
    var endTime: Long? = null
)