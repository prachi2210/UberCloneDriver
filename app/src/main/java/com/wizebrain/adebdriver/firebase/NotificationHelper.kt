package com.wizebrain.adebdriver.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.activity.SplashScreenActivity
import com.wizebrain.adebdriver.ui.map.DriverMapActivityScreen
import com.wizebrain.adebdriver.utils.Constants


object NotificationHelper {

    fun displayNotification(
        context: Context, title: String?,
        message: String?, type: String, remoteMessage: RemoteMessage
    ) {
        val intent = Intent(context, DriverMapActivityScreen::class.java)
        if (type.equals(Constants.bookingConfirmation)) {
            intent.putExtra(Constants.NAME, remoteMessage.data["name"])
            intent.putExtra(Constants.PHOTO, remoteMessage.data["photo"])
            intent.putExtra(Constants.PRICE, remoteMessage.data["price"])
            intent.putExtra(Constants.RIDEID, remoteMessage.data["rideid"])
            intent.putExtra(Constants.PICKUPADDFRESS, remoteMessage.data["pickupAddress"])
            intent.putExtra(Constants.DROPOFFADDRESS, remoteMessage.data["dropOffAddress"])



        }

      /*  var intent = Intent(getString(R.string.action_device_token))
        intent.putExtra(Constants.NOTIFICATION_BROADCASTER, token)
        sendBroadcast(intent)*/




        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            context,
            Constants.CHANNE_ID
        )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setVibrate(
                longArrayOf(
                    1000, 1000, 1000,
                    1000, 1000
                )
            )
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

   /*     if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.JELLY_BEAN
        ) {
            builder = builder.setContent(
                getCustomDesign(context, title, message)
            )
        } // If Android Version is lower than Jelly Beans,
        else {
            builder = builder.setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
        }*/


        builder = builder.setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.logo)


        val notificationManager = context.getSystemService(
            FirebaseMessagingService.NOTIFICATION_SERVICE
        ) as NotificationManager
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                Constants.CHANNE_ID, Constants.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
        notificationManager.notify(0, builder.build())

    }


    private fun getCustomDesign(
        context: Context, title: String?,
        message: String?
    ): RemoteViews {
        val remoteViews = RemoteViews(
            context.packageName,
            R.layout.notification
        )
        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.message, message)
        remoteViews.setImageViewResource(
            R.id.icon,
            R.drawable.logo
        )
        return remoteViews


    }
}