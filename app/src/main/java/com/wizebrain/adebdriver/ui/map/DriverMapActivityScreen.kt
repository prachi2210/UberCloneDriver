package com.wizebrain.adebdriver.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.directions.route.*
import com.example.adebuser.base.BaseActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.databinding.ActivityDriverMapScreenBinding
import com.wizebrain.adebdriver.extensions.hide
import com.wizebrain.adebdriver.extensions.show
import com.wizebrain.adebdriver.ui.map.ride.UserRideDetailsFragment
import com.wizebrain.adebdriver.ui.map.ride.listener.UserRideAcceptRejectListener
import java.util.*

class DriverMapActivityScreen : BaseActivity(), View.OnClickListener, OnMapReadyCallback,
    RoutingListener , UserRideAcceptRejectListener, GoogleMap.OnMapClickListener {
    private val TAG: String = DriverMapActivityScreen::class.java.simpleName
    private lateinit var binding: ActivityDriverMapScreenBinding
    private lateinit var mLocation: Location
    private lateinit var locationCallback: LocationCallback
    private var mMap: GoogleMap? = null
    private val LOCATION_REQUEST_CODE = 23
    var locationPermission = false
    private var googleMap: GoogleMap? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 999
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var mLocationManager: LocationManager? = null
    private var mLocationRequest: LocationRequest? = null

    private var startPoint: LatLng? = null
    private var endPoint: LatLng? = null
    private var polylines: ArrayList<Polyline>? = null


    private val userRideRequestFragment by lazy { UserRideRequestFragment(this) }
    private val userRideDetailsFragment by lazy { UserRideDetailsFragment() }

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
        openFragment(userRideRequestFragment,R.id.frame_container_request)
        setUpLocationListener()
        getLocation()


        binding.swipeButton.onSwipedListener = {
            Log.d(TAG, "onSwiped")
        }

        binding.swipeButton.onSwipedOnListener = {
            Log.d(TAG, "onSwipedOn")
            binding.frameOnline.show()

        }

        binding.swipeButton.onSwipedOffListener = {
            Log.d(TAG, "onSwipedOff")
            binding.frameOnline.hide()
        }

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

    override fun onMapReady(p0: GoogleMap) {
        Log.e(TAG, "onMapReady $p0")


        googleMap = p0
        googleMap!!.setOnMapClickListener(this)
        getLocation()


    }

    private fun getLocation() {
        mLocationRequest =
            LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)


    }


    private fun findroutes(startPoint: LatLng?, endPoint: LatLng?) {
        if (startPoint == null || endPoint == null) {

            Toast.makeText(this, "Unable to get location", Toast.LENGTH_LONG).show()
        } else {
            val routing = Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(startPoint, endPoint)
                .key("AIzaSyCh8iJgNF3h-edODUQBvPBPq2TaNYyIWsQ") //also define your api key here.
                .build()
            routing.execute()
        }
    }


    override fun onRoutingFailure(p0: RouteException?) {

    }

    override fun onRoutingStart() {

    }

    override fun onRoutingSuccess(route: ArrayList<Route>?, shortestRouteIndex: Int) {
        val center = CameraUpdateFactory.newLatLng(startPoint)
        val zoom = CameraUpdateFactory.zoomTo(16f)
        if (polylines != null) {
            polylines?.clear()
        }
        val polyOptions = PolylineOptions()
        var polylineStartLatLng: LatLng? = null
        var polylineEndLatLng: LatLng? = null


        polylines = ArrayList<Polyline>()
        //add route(s) to the map using polyline
        //add route(s) to the map using polyline
        for (i in route!!.indices) {
            if (i == shortestRouteIndex) {
                polyOptions.color(ContextCompat.getColor(this, R.color.text_blue))
                polyOptions.width(14f)
                polyOptions.addAll(route[shortestRouteIndex].points)
                val polyline: Polyline = googleMap!!.addPolyline(polyOptions)
                polylineStartLatLng = polyline.points[0]
                val k = polyline.points.size
                polylineEndLatLng = polyline.points[k - 1]
                polylines?.add(polyline)
            }
        }


        //Add Marker on route starting position

        //Add Marker on route starting position
        val startMarker = MarkerOptions()
        startMarker.position(polylineStartLatLng)
        startMarker.title("My Location")
        googleMap?.addMarker(startMarker)

        //Add Marker on route ending position

        //Add Marker on route ending position
        val endMarker = MarkerOptions()
        endMarker.position(polylineEndLatLng)
        endMarker.title("Destination")
        googleMap?.addMarker(endMarker)


    }

    override fun onRoutingCancelled() {

    }

    private fun openFragment(fragment: Fragment,id:Int) = supportFragmentManager.beginTransaction().apply {
        replace(id, fragment)
        commit()
    }

    override fun onAcceptRejectClose(type: Int, position: Int) {
        Log.e(TAG,"acceptReject $type position=$position")
        binding.frameContainerRequest.hide()
        binding.frameRideStart.show()
        openFragment(userRideDetailsFragment,R.id.frame_ride_start)
    }





    private fun setUpLocationListener() {

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        mLocationManager =
         getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationCallback = object : LocationCallback() {
            @SuppressLint("MissingPermission")
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    mLocation = location

                    googleMap!!.isMyLocationEnabled = true
                    val geocoder: Geocoder = Geocoder(this@DriverMapActivityScreen, Locale.getDefault())

                    val addresses: List<Address> = geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    )

                    val address: String =
                        addresses[0].getAddressLine(0)


                    //   binding.tvCurrentAddress.text = addresses[0].featureName
             /*       binding.tvCurrentAddress.text = address*/
                    val ltlng = LatLng(location.latitude, location.longitude)
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        ltlng, 16f
                    )
                    googleMap!!.animateCamera(cameraUpdate)

                }
            }
        }


//      fusedLocationProviderClient =
//            LocationServices.getFusedLocationProviderClient(requireActivity())
//        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
//            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//
//        fusedLocationProviderClient.requestLocationUpdates(
//            locationRequest,
//            object : LocationCallback() {
//                override fun onLocationResult(locationResult: LocationResult) {
//                    super.onLocationResult(locationResult)
//                    for (location in locationResult.locations) {
//
//                        mLocation = location
//
//                        googleMap!!.isMyLocationEnabled = true
//                        val geocoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())
//
//                        val addresses: List<Address> = geocoder.getFromLocation(
//                            location.latitude,
//                            location.longitude,
//                            1
//                        )
//
//                        val address: String =
//                            addresses[0].getAddressLine(0)
//
//
//                      binding.tvCurrentAddress.text = addresses[0].featureName
//                       // binding.tvCurrentAddress.text = address
//                        val ltlng = LatLng(location.latitude, location.longitude)
//                        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
//                            ltlng, 16f
//                        )
//                        googleMap!!.animateCamera(cameraUpdate)
//
//                    }
//                    // Few more things we can do here:
//                    // For example: Update the location of user on server
//                }
//            },
//            Looper.myLooper()
        //       )


    }

    override fun onMapClick(latLng: LatLng) {
        endPoint = latLng
        googleMap?.clear()


        startPoint = LatLng(
            mLocation?.latitude,
            mLocation?.longitude
        )
        val geocoder: Geocoder = Geocoder(this, Locale.getDefault())

        val addresses: List<Address> = geocoder.getFromLocation(
            endPoint!!.latitude,
            endPoint!!.longitude,
            1
        )

        val address: String =
            addresses[0].getAddressLine(0)


        //start route finding
        findroutes(startPoint, endPoint)
    }
}