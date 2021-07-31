package com.wizebrain.adebdriver.services

import android.Manifest
import android.R.attr.delay
import android.app.*
import android.app.job.JobParameters
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.ui.auth.response.MessageResponse
import com.wizebrain.adebdriver.ui.map.DriverMapActivityScreen
import com.wizebrain.adebdriver.ui.map.MapViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val SAVED_INT_KEY = "int_key";

// Small play code for JobSchedulers
class UpdateLocationService : Service() {

    lateinit var params: JobParameters
    var TAG = UpdateLocationService::class.java.simpleName
    var latitude = ""
    var longitude = ""
    private lateinit var viewModel: MapViewModel
    var delay = 5000
    var handler = Handler()
    private lateinit var runnable: Runnable


    // Whenever the contraints are satisfied this will get fired.
    //If the job fails for some reason, return true from on the onStopJob to restart the job
    // The onStartJob is performed in the main thread, if you start asynchronous processing in this method, return true otherwise false

/*    override fun onStartJob(params: JobParameters?): Boolean {
        // We land here when system calls our job.
        this.params = params!!




        return true     // Our task will run in background, we will take care of notifying the finish.
    }*/


//driverLocationUpdateII


    private fun driverLocationUpdate(driverRef: String) {
        val driverRef = driverRef.toRequestBody("multipart/form-data".toMediaType())
        val latitude = latitude.toRequestBody("multipart/form-data".toMediaType())
        val longitude = longitude.toRequestBody("multipart/form-data".toMediaType())
        val call = RetrofitBuilder.apiService
        call.driverLocationUpdateII(driverRef, latitude, longitude).enqueue(
            object : Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    Log.e(TAG, "OnResponse Success ${response.message()}")
                    //     jobFinished(params,false)
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.e(TAG, "OnResponse Failure ${t.message}")
                }

            })


    }


    private fun getCurrentLatLng() {
        var fusedLocationProviderClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val task: Task<*> = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener { location: Any ->
                latitude = (location as Location).latitude.toString()
                longitude = (location as Location).longitude.toString()

            }
        } else {

        }

    }

    /* override fun onStopJob(params: JobParameters?): Boolean {
         // Cancel the counter task.
         Log.e(TAG, "Job paused.")

         return false
         // I want it to reschedule so returned true, if we would have returned false, then job would have ended here.
         // It would not fire onStartJob() when constraints are re satisfied.
     }*/

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent!!.getStringExtra(com.wizebrain.adebdriver.utils.Constants.USER_ID)
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable, delay.toLong())
            Log.e(TAG, "onStartCommand ${input}")
            LocalBroadcastManager.getInstance(this)
            var intent = Intent(getString(R.string.location_update))
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            getCurrentLatLng()

            driverLocationUpdate(
                input.toString()
            )


            Log.e(TAG, "This method is run every 10 seconds")

        }.also { runnable = it }, delay.toLong())



        createNotificationChannel()
        val notificationIntent = Intent(this, DriverMapActivityScreen::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification: Notification =
            NotificationCompat.Builder(this, com.wizebrain.adebdriver.utils.Constants.CHANNE_ID)
                .setContentTitle("ADEB RIDE")
                .setContentText("You are on the way")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build()
        startForeground(1, notification)
        //do heavy work on a background thread
        //stopSelf();
        //do heavy work on a background thread
        //stopSelf();
        return START_STICKY
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy()
    }

    /**
     * Task which performs the counting with added delay. Before it starts, it picks up the value
     * which has been already printed from previous onStartJob() calls.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                com.wizebrain.adebdriver.utils.Constants.CHANNE_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }
}