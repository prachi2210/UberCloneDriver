package com.wizebrain.adebdriver.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wizebrain.adebdriver.MyApplication
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.activity.SplashScreenActivity
import com.wizebrain.adebdriver.ui.map.DriverMapActivityScreen
import com.wizebrain.adebdriver.utils.Constants
import com.wizebrain.adebdriver.utils.Constants.bookingConfirmation
import org.json.JSONObject


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = MyFirebaseMessagingService::class.java.simpleName
    private var type = ""
    private var notifyType = ""
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.e(TAG, "onMessageReceived body remoteMessage fist called")
        Log.e(TAG, "onMessageReceived body remoteMessage ${remoteMessage.data.toString()}")
        type = if (remoteMessage.data != null && remoteMessage.data["type"] != null && remoteMessage.data["type"].toString().isNotEmpty()) {
            remoteMessage.data["type"].toString()

        } else{
            remoteMessage.data["notify"].toString()
        }



        if (remoteMessage.notification != null) {

            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body

            displayNotification(
                applicationContext,
                title!!,
                body!!,
                type,
                remoteMessage
            )


        }


    }


    private fun displayNotification(
        context: Context, title: String?,
        message: String?, type: String, remoteMessage: RemoteMessage
    ) {
        //


        /*{
   "notifyType=ride canceled",
   userName=test user 10,
   userPhoneNumber=0123456789,
   userRating=3





}*/



        if (type.equals(Constants.rideCancelled)) {


        /*    "notifyType=ride canceled",
            userName=test user 10,
            userPhoneNumber=0123456789,
            userRating=3*/


            if (MyApplication.active) {
                Log.e(TAG, "notifyType active")
                var intent = Intent(getString(R.string.action_notification_foreground))
                intent.putExtra(Constants.NAME, remoteMessage.data["userName"])
                intent.putExtra(Constants.PHONENUMBER, remoteMessage.data["userPhoneNumber"])
                intent.putExtra(Constants.RATING, remoteMessage.data["userRating"])
               intent.putExtra(Constants.TYPE, notifyType)
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            } else {
                Log.e(TAG, "background active")
                Log.e(TAG, "MyApplication in background")
                val intent = Intent(context, DriverMapActivityScreen::class.java)
                intent.putExtra(Constants.NAME, remoteMessage.data["userName"])
                intent.putExtra(Constants.PHONENUMBER, remoteMessage.data["userPhoneNumber"])
                intent.putExtra(Constants.RATING, remoteMessage.data["userRating"])
                intent.putExtra(Constants.TYPE, notifyType)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                val pendingIntent = PendingIntent.getActivity(
                    this, 0, intent,
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

        }



        if (type.equals(bookingConfirmation)) {
            if (MyApplication.active) {
                Log.e(TAG, "MyApplication active")
                var intent = Intent(getString(R.string.action_notification_foreground))
                intent.putExtra(Constants.NAME, remoteMessage.data["name"])
                intent.putExtra(Constants.PHOTO, remoteMessage.data["photo"])
                intent.putExtra(Constants.PRICE, remoteMessage.data["price"])
                intent.putExtra(Constants.RIDEID, remoteMessage.data["rideid"])
                intent.putExtra(Constants.PICKUPADDFRESS, remoteMessage.data["pickupAddress"])
                intent.putExtra(Constants.DROPOFFADDRESS, remoteMessage.data["dropOffAddress"])
                intent.putExtra(Constants.TYPE, type)
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            } else {

                Log.e(TAG, "MyApplication in background")
                val intent = Intent(context, DriverMapActivityScreen::class.java)
                intent.putExtra(Constants.NAME, remoteMessage.data["name"])
                intent.putExtra(Constants.PHOTO, remoteMessage.data["photo"])
                intent.putExtra(Constants.PRICE, remoteMessage.data["price"])
                intent.putExtra(Constants.RIDEID, remoteMessage.data["rideid"])
                intent.putExtra(Constants.PICKUPADDFRESS, remoteMessage.data["pickupAddress"])
                intent.putExtra(Constants.DROPOFFADDRESS, remoteMessage.data["dropOffAddress"])
                intent.putExtra(Constants.TYPE, type)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                val pendingIntent = PendingIntent.getActivity(
                    this, 0, intent,
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
        }


    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        var intent = Intent(getString(R.string.action_device_token))
        intent.putExtra(Constants.DEVICE_TOKEN, token)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

    }



}


