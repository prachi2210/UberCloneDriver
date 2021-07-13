package com.wizebrain.adebdriver.ui.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.directions.route.Route
import com.directions.route.RouteException
import com.directions.route.RoutingListener
import com.example.adebuser.base.BaseActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.activity.SplashScreenActivity
import com.wizebrain.adebdriver.databinding.ActivityDriverMapScreenBinding
import com.wizebrain.adebdriver.databinding.ActivityLoginBinding
import com.wizebrain.adebdriver.ui.auth.AuthViewModel
import com.wizebrain.adebdriver.ui.auth.LoginActivity
import java.util.ArrayList

class DriverMapActivityScreen : BaseActivity(), View.OnClickListener, OnMapReadyCallback,
    RoutingListener {
    private val TAG: String = DriverMapActivityScreen::class.java.simpleName
    private lateinit var binding: ActivityDriverMapScreenBinding
    private lateinit var mLocation: Location
    private lateinit var locationCallback: LocationCallback
    private var mMap: GoogleMap? = null
    private val LOCATION_REQUEST_CODE = 23
    var locationPermission = false
    private var startPoint: LatLng? = null
    private var endPoint: LatLng? = null
    private var polylines: ArrayList<Polyline>? = null

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DriverMapActivityScreen::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverMapScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermission()
        binding.map.onCreate(savedInstanceState)
        binding.map.onResume()
        try {
            MapsInitializer.initialize(this.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.map.getMapAsync(this)
        openFragment(UserRideRequestFragment())

    }


    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_REQUEST_CODE
            )
        } else {
            locationPermission = true
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    //if permission granted.
                    locationPermission = true

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onClick(v: View?) {

        when (v) {
            binding.ivProfile -> {

            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.e(TAG, "onMapReady $googleMap")
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

    override fun onRoutingFailure(p0: RouteException?) {

    }

    override fun onRoutingStart() {

    }

    override fun onRoutingSuccess(p0: ArrayList<Route>?, p1: Int) {

    }

    override fun onRoutingCancelled() {

    }

    private fun openFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.frame_container_request, fragment)
        commit()
    }


}