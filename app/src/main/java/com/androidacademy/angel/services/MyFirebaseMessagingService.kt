package com.androidacademy.angel.services


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.androidacademy.angel.Const.INTENT_KEY
import com.androidacademy.angel.Const.OPEN_FRAGMENT
import com.androidacademy.angel.MainActivity
import com.androidacademy.angel.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val CHANNEL_ID = "CHANNEL_ID"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        sendNotification(message.data.get("title") ?: "", message.data.get("msg") ?: "")


        super.onMessageReceived(message)

    }

    private fun sendNotification(msg: String, title: String) {

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
                CHANNEL_ID, "My channel", // TODO
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "My channel description" // TODO
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(application, CHANNEL_ID)
            .setSmallIcon(R.drawable.angel_logo)
            .setContentText(msg)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentIntent(pendingIntent)
        notificationManager.notify(1, notification.build())

    }
}
