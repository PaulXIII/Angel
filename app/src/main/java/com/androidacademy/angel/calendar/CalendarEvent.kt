package com.androidacademy.angel.calendar

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import com.androidacademy.angel.data.AdvertModel
import java.util.*

    fun createCalendarEvent(context: Context,advertModel: AdvertModel) {


        val begindate:Date=advertModel.startTime
        val enddate:Date=advertModel.endTime
        val beginTime = Calendar.getInstance()
        beginTime.time=begindate

        val endTime = Calendar.getInstance()
        endTime.time=enddate

        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
            .putExtra(CalendarContract.Events.TITLE, advertModel.title)
            .putExtra(CalendarContract.Events.DESCRIPTION, advertModel.description)
            .putExtra(
                CalendarContract.Events.AVAILABILITY,
                CalendarContract.Events.AVAILABILITY_BUSY
            )
        context.startActivity(intent)
    }
