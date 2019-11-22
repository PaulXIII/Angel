package com.androidacademy.angel.services


import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import com.androidacademy.angel.R
import com.androidacademy.angel.MainActivity
import android.app.PendingIntent
import com.androidacademy.angel.Const.INTENT_KEY
import com.androidacademy.angel.Const.OPEN_FRAGMENT


class MyFirebaseMessagingService : FirebaseMessagingService() {

    val CHANNEL_ID = "CHANNEL_ID"


    override fun onMessageReceived(message: RemoteMessage) {
        sendNotification(message.notification?.body ?: "")
        super.onMessageReceived(message)

    }

    private fun sendNotification(msg: String){

        val showActivityIntent = Intent(application, MainActivity::class.java)
        showActivityIntent.putExtra(INTENT_KEY, OPEN_FRAGMENT)


        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            showActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

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
            .setContentText(msg)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        notificationManager.notify(1, notification.build())

    }
}
