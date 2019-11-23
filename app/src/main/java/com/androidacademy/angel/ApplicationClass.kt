package com.androidacademy.angel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.messaging.FirebaseMessaging

val prefs: Prefs by lazy {
    ApplicationClass.preferences!!
}

class ApplicationClass: Application() {

    companion object{
        var preferences: Prefs? = null
    }

    override fun onCreate() {
        preferences = Prefs(applicationContext)
        FirebaseMessaging.getInstance().subscribeToTopic("global")
        super.onCreate()
    }
}

class Prefs(context: Context){
    private val PREFS_FILENAME = "com.androidacademy.angel.prefsField"
    private val LOG_IN_ID = "log_in_id"
    private val prefsField: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var idClient: String?
        get() = prefsField.getString(LOG_IN_ID, null)
        set(value : String?) = prefsField.edit().putString(LOG_IN_ID, value).apply()
}