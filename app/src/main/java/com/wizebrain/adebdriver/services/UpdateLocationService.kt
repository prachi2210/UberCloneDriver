package com.wizebrain.adebdriver.services

import android.Manifest
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.ViewModelProviderFactory
import com.wizebrain.adebdriver.data.api.ApiHelper
import com.wizebrain.adebdriver.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.ui.map.MapViewModel

const val SAVED_INT_KEY = "int_key";

// Small play code for JobSchedulers
class UpdateLocationService : JobService() {

    lateinit var params: JobParameters
    var TAG = UpdateLocationService::class.java.simpleName
    var latitude = ""
    var longitude = ""

    // Whenever the contraints are satisfied this will get fired.

    override fun onStartJob(params: JobParameters?): Boolean {
        // We land here when system calls our job.
        this.params = params!!
        Log.e(TAG, "Service is started")

        LocalBroadcastManager.getInstance(this)
        var intent = Intent(getString(R.string.location_update))
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)




        return true     // Our task will run in background, we will take care of notifying the finish.
    }






    private fun getCurrentLatLng()
    {   var fusedLocationProviderClient: FusedLocationProviderClient =
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

    override fun onStopJob(params: JobParameters?): Boolean {
        // Cancel the counter task.
        Log.d(UpdateLocationService::class.java.simpleName, "Job paused.")
        return true
        // I want it to reschedule so returned true, if we would have returned false, then job would have ended here.
        // It would not fire onStartJob() when constraints are re satisfied.
    }


    fun notifyJobFinished() {
        Log.d(UpdateLocationService::class.java.simpleName, "Job finished. Calling jobFinished()")
        val prefs = getSharedPreferences("deep_service", Context.MODE_PRIVATE)
        // Try to fetch a preference.
        prefs.edit().putInt(SAVED_INT_KEY, 0).apply()
        // Job has finished now, calling jobFinishedI(false) will release all resources and
        // false as we do not want it to reschedule as the job is done now.
        jobFinished(params, false)
    }


    /**
     * Task which performs the counting with added delay. Before it starts, it picks up the value
     * which has been already printed from previous onStartJob() calls.
     */

}