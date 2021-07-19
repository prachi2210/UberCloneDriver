package com.wizebrain.adebdriver.ui.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.directions.route.*
import com.wizebrain.adebdriver.base.BaseActivity
import com.example.adebuser.data.api.RetrofitBuilder
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.activity.RateUserActivity
import com.wizebrain.adebdriver.base.ViewModelProviderFactory
import com.wizebrain.adebdriver.data.api.ApiHelper
import com.wizebrain.adebdriver.databinding.ActivityDriverMapScreenBinding
import com.wizebrain.adebdriver.extensions.hide
import com.wizebrain.adebdriver.extensions.show
import com.wizebrain.adebdriver.ui.map.response.RideData
import com.wizebrain.adebdriver.ui.map.response.RideInfo
import com.wizebrain.adebdriver.ui.map.ride.StartRideFragment
import com.wizebrain.adebdriver.ui.map.ride.listener.UserRideListener
import com.wizebrain.adebdriver.utils.ActivityStarter
import com.wizebrain.adebdriver.utils.PermissionUtils
import com.wizebrain.adebdriver.utils.Status
import kotlin.collections.ArrayList


class DriverMapActivityScreen : BaseActivity(), View.OnClickListener, OnMapReadyCallback,
    RoutingListener, UserRideListener, GoogleMap.OnMapClickListener {
    private val TAG: String = DriverMapActivityScreen::class.java.simpleName
    private lateinit var binding: ActivityDriverMapScreenBinding
    private lateinit var mLocation: Location
    private lateinit var locationCallback: LocationCallback
    private val LOCATION_REQUEST_CODE = 23
    var locationPermission = false
    private lateinit var viewModel: MapViewModel
    private var googleMap: GoogleMap? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var mLocationManager: LocationManager? = null
    private var mLocationRequest: LocationRequest? = null
    private var startPoint: LatLng? = null
    private var endPoint: LatLng? = null
    private var polylines: ArrayList<Polyline>? = null
    var startingLocation = ""
    var endingLocation = ""
    private var rideInfo: RideInfo? = null
    var count = 0



    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DriverMapActivityScreen::class.java)
        }
    }


    override fun onResume() {
        super.onResume()
      //  startLocationUpdates()
    }

    override fun onStart() {
        super.onStart()
        when {
            PermissionUtils.isAccessFineLocationGranted(this) -> {
                when {
                    PermissionUtils.isLocationEnabled(this) -> {
                        setUpLocationListener()
                    }
                    else -> {
                        PermissionUtils.showGPSNotEnabledDialog(this)
                    }
                }
            }
            else -> {
                PermissionUtils.requestAccessFineLocationPermission(
                    this,
                    LOCATION_REQUEST_CODE
                )
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverMapScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermission()
        setUpLocationListener()

        binding.map.onCreate(savedInstanceState)
        binding.map.onResume()
        try {
            MapsInitializer.initialize(this.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.map.getMapAsync(this)





        setupViewModel()


        binding.swipeButton.onSwipedListener = {

        }

        binding.swipeButton.onSwipedOnListener = {
            binding.frameNotification.hide()
            //getMyBookings()
            binding.frameContainerRequest.show()

        }

        binding.swipeButton.onSwipedOffListener = {
            binding.frameNotification.show()
            binding.frameContainerRequest.hide()
            binding.frameRideStart.hide()
        }

    }


    private fun resizeFragment(newWidth: Int, newHeight: Int) {
/*        val view = StartRideFragment().view
        val p = RelativeLayout.LayoutParams(newWidth, newHeight)
        view!!.layoutParams = p
        view.requestLayout()*/
    }

    override fun openClose(type: Int) {


    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MapViewModel::class.java)
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        PermissionUtils.isLocationEnabled(this) -> {
                            locationPermission = true
                            setUpLocationListener()
                        }
                        else -> {
                            locationPermission = true
                            PermissionUtils.showGPSNotEnabledDialog(this)
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.location_permission_not_granted),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }




    override fun onClick(v: View?) {

        when (v) {
            binding.ivProfile -> {

            }

                binding.tvOpenFragment->
                {
                    if(count==0)
                    {
                       count=1
                        binding.frameRideStart.hide()


                    }
                    else
                    {
                        count=0
                        binding.frameRideStart.show()
                    }
                }
        }
    }


    private fun getMyBookings() {
        viewModel.getBookingByDriver(
            userPreferences.getUserREf().trim()
        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {
                                if (user.body()?.rideData?.rideId.isNullOrEmpty()) {
                                    binding.frameContainerRequest.hide()
                                    setError(user.body()?.msg.toString() + getString(R.string.ride_notified_msg))
                                } else {
                                    binding.frameContainerRequest.show()
                                    openFragment(
                                        UserRideRequestFragment.newInstance(
                                            user.body()?.rideData
                                        ),
                                        R.id.frame_container_request
                                    )
                                }


                            } else {
                                setError(user.body()?.msg.toString())
                            }
                        }
                    }
                    Status.ERROR -> {
                        dismissDialog()
                        setError(it.message.toString())

                    }
                    Status.LOADING -> {
                        showDialog()
                    }
                }
            }
        })

    }


    override fun onAcceptRejectClose(
        type: Int,
        rideData: RideData?,

        ) {
        acceptReject(type.toString(), rideData)
    }

    override fun onStartTrip(rideInfo: RideInfo?) {
        //taking routes
        //finding routes
        var pickUpLat = rideInfo?.pickupLat.toString().trim().toDouble()
        var pickUpLong =
            rideInfo?.pickupLog.toString().trim().toDouble()
        var dropOffLat =
            rideInfo?.dropOffLat.toString().trim().toDouble()
        var dropOffLong =
            rideInfo?.dropOffLog.toString().trim().toDouble()
        var startLatLong = LatLng(pickUpLat, pickUpLong)
        var endLatLong = LatLng(dropOffLat, dropOffLong)
        startPoint = startLatLong
        endPoint = endLatLong
        startingLocation = rideInfo?.pickupName.toString().trim()
        endingLocation = rideInfo?.dropOffName.toString().trim()
        findroutes(startLatLong, endLatLong)
       startTrip(rideInfo?.rideId.toString().trim(), "1")


    }

    override fun onEndTrip(rideInfo: RideInfo?) {
       startTrip(rideInfo?.rideId.toString().trim(), "0")

    }

    private fun startTrip(rideId: String, type: String) {
        viewModel.startTrip(
            rideId, type
        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {
                                //Nothing happen start routing
                                if (type.equals("0")) {
                                    //open activity
                                    ActivityStarter.of(
                                        RateUserActivity.getStartIntent(
                                            this,
                                            rideInfo?.rideId.toString(),
                                            rideInfo?.userRef.toString(),
                                            rideInfo?.userName.toString()
                                        )
                                    )
                                        .finishCurrentActivity()
                                        .startFrom(this)


                                } else if (type.equals("1")) {

                                }


                            } else {
                                setError(user.body()?.msg.toString())
                            }
                        }
                    }
                    Status.ERROR -> {
                        dismissDialog()
                        setError(it.message.toString())

                    }
                    Status.LOADING -> {
                        showDialog()
                    }
                }
            }
        })
    }

    private fun acceptReject(
        type: String,
        rideData: RideData?
    ) {
        viewModel.acceptRideByDriver(
            userPreferences.getUserREf().trim(),
            rideData?.rideId.toString(),
            type

        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {
                                if (user.body()?.msg.equals("You have rejected ride")) {
                                    getMyBookings()
                                } else {
                                    binding.frameContainerRequest.hide()
                                    binding.frameRideStart.show()
                                    rideInfo = user.body()?.rideInfo

                                    openFragment(
                                        StartRideFragment.newInstance(
                                            user.body()?.rideInfo, type
                                        ), R.id.frame_ride_start
                                    )

                                }

                            } else {
                                setError(user.body()?.msg.toString())
                            }
                        }
                    }
                    Status.ERROR -> {
                        dismissDialog()
                        setError(it.message.toString())

                    }
                    Status.LOADING -> {
                        showDialog()
                    }
                }
            }
        })

    }


    override fun onMapReady(p0: GoogleMap) {
        Log.e(TAG, "onMapReady $p0")
        googleMap = p0
        googleMap!!.setOnMapClickListener(this)



  /*      val latLng = LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())
        val markerOptions = MarkerOptions().position(latLng)
        googleMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap!!.addMarker(markerOptions)*/
    }



    /*find routes*/

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
        Log.e(TAG, "onRoutingFailure Exception  ${p0?.message}")

    }

    override fun onRoutingStart() {

    }

    override fun onRoutingSuccess(route: ArrayList<Route>?, shortestRouteIndex: Int) {
        val center = CameraUpdateFactory.newLatLng(startPoint)
        val zoom = CameraUpdateFactory.zoomTo(16f)
        if (polylines != null) {
            polylines?.clear()
        }

        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint, 14f))


        /*mapfragment.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))*/

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

        //titile daalfo
        startMarker.title(startingLocation)
        googleMap?.addMarker(startMarker)

        //Add Marker on route ending position

        //Add Marker on route ending position
        val endMarker = MarkerOptions()
        endMarker.position(polylineEndLatLng)
        endMarker.title(endingLocation)
        googleMap?.addMarker(endMarker)


    }

    override fun onRoutingCancelled() {

    }

    private fun openFragment(fragment: Fragment, id: Int) =
        supportFragmentManager.beginTransaction().apply {
            replace(id, fragment)
            commit()
        }


/*    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        mLocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationProviderClient.requestLocationUpdates(
            mLocationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }*/


    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }


    private fun setUpLocationListener() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // for getting the current location update after every 2 seconds with high accuracy
        val locationRequest = LocationRequest.create().
        setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    for (location in locationResult.locations) {
                      /*  latTextView.text = location.latitude.toString()
                        lngTextView.text = location.longitude.toString()*/
                        //here we get current get latitide
                        val latLng = LatLng(location.latitude, location.longitude)
                        val markerOptions = MarkerOptions().position(latLng)
                        googleMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                        googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                        googleMap!!.addMarker(markerOptions)

                    }
                    // Few more things we can do here:
                    // For example: Update the location of user on server
                }
            },
            Looper.myLooper()
        )

    }

    override fun onMapClick(latLng: LatLng) {
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}