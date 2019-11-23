package com.androidacademy.angel.details

import android.content.Context
import androidx.lifecycle.ViewModel
import com.androidacademy.angel.calendar.createCalendarEvent
import com.androidacademy.angel.data.AdvertModel

class DetailsViewModel : ViewModel() {

    fun addToCalendar(context: Context?, advertModel: AdvertModel) {
        context?.let {
            createCalendarEvent(it, advertModel)
        }
    }
}
