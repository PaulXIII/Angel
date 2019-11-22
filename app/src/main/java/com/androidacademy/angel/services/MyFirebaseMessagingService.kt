package com.androidacademy.angel.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.androidacademy.angel.R


class MyFirebaseMessagingService : FirebaseMessagingService() {

    val CHANNEL_ID = "CHANNEL_ID"

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("WTF", "MSG NOTIFICATION")
        sendNotification(message.notification?.body ?: "")
        super.onMessageReceived(message)

    }

    private fun sendNotification(msg: String){
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "My channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "My channel description"
            notificationManager.createNotificationChannel(channel)
        }




        val notification = NotificationCompat.Builder(application, CHANNEL_ID)
            .setSmallIcon(R.drawable.angel_logo)
            .setContentTitle("New notification")
            .setContentText(msg)
            .setAutoCancel(true)
        notificationManager.notify(0, notification.build())

    }
}
