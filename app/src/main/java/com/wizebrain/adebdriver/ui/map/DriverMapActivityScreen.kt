package com.wizebrain.adebdriver.ui.map

import android.Manifest
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.directions.route.*
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.wizebrain.adebdriver.MyApplication
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.BaseActivity
import com.wizebrain.adebdriver.base.ViewModelProviderFactory
import com.wizebrain.adebdriver.data.api.ApiHelper
import com.wizebrain.adebdriver.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.databinding.ActivityDriverMapScreenBinding
import com.wizebrain.adebdriver.extensions.hide
import com.wizebrain.adebdriver.extensions.loadImage
import com.wizebrain.adebdriver.extensions.show
import com.wizebrain.adebdriver.services.UpdateLocationService
import com.wizebrain.adebdriver.ui.map.response.RideInfo
import com.wizebrain.adebdriver.ui.map.ride.StartRideFragment
import com.wizebrain.adebdriver.ui.map.ride.listener.UserRideListener
import com.wizebrain.adebdriver.ui.profile.DriverProfileActivity
import com.wizebrain.adebdriver.ui.rate.RateUserActivity
import com.wizebrain.adebdriver.utils.ActivityStarter
import com.wizebrain.adebdriver.utils.Constants
import com.wizebrain.adebdriver.utils.PermissionUtils
import com.wizebrain.adebdriver.utils.Status


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
    private var startPoint: LatLng? = null
    private var endPoint: LatLng? = null
    private var polylines: ArrayList<Polyline>? = null
    var startingLocation = ""
    var endingLocation = ""
    private var rideInfo: RideInfo? = null
    var count = 0
    var clientRiderName = ""
    var clientRiderPhoto = ""
    var clientRiderPrice = ""
    var clientRiderRideId = ""
    var clientRiderPickupAddress = ""
    var clientRiderDropOffAddress = ""
    private lateinit var jobScheduler: JobScheduler
    private var destinationLatitude:String=""
    private var  destinationLogitude:String=""


    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DriverMapActivityScreen::class.java)
        }
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val bundle = intent?.extras
        if (bundle != null) {
            if (bundle.getString(Constants.TYPE) != null) {
                Log.e(TAG, "getType ${bundle.getString(Constants.TYPE)}")
            }
        }


        Log.e(TAG, "ON new Intent called")
        Log.e(TAG, "ON new Intent Withpout Bundel called ${intent?.getStringExtra(Constants.TYPE)}")
        Log.e(TAG, "ON new Intent Withpout Bundel called ${bundle?.getString(Constants.TYPE)}")




        if (intent?.getStringExtra(Constants.TYPE).toString()
                .equals(Constants.bookingConfirmation)
        ) {

            clientRiderName = intent?.getStringExtra(Constants.NAME).toString().trim()
            clientRiderPhoto = intent?.getStringExtra(Constants.PHOTO).toString().trim()
            clientRiderPrice = intent?.getStringExtra(Constants.PRICE).toString().trim()
            clientRiderRideId = intent?.getStringExtra(Constants.RIDEID).toString().trim()
            clientRiderPickupAddress =
                intent?.getStringExtra(Constants.PICKUPADDFRESS).toString().trim()
            clientRiderDropOffAddress =
                intent?.getStringExtra(Constants.DROPOFFADDRESS).toString().trim()



            openFragment(
                UserRideRequestFragment.newInstance(
                    clientRiderName,
                    clientRiderPhoto,
                    clientRiderPrice,
                    clientRiderRideId,
                    clientRiderPickupAddress,
                    clientRiderDropOffAddress, "0"
                )
            )


        } else if (intent?.getStringExtra(Constants.TYPE).toString() == Constants.rideCancelled
        ) {
            rideCancelled()
        }


    }


    override fun onResume() {
        super.onResume()

        //  startLocationUpdates()
    }


    override fun onStop() {
        super.onStop()
        MyApplication.active = false
    }

    override fun onStart() {
        super.onStart()
        MyApplication.active = true
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


    var notificationBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e(TAG, "onBroadCastReceiver before intent${intent}")
            Log.e(TAG, "onBroadCastReceiver Name ${intent?.getStringExtra(Constants.TYPE)}")
            if (intent?.getStringExtra(Constants.TYPE).toString() != null) {

                if (intent?.getStringExtra(Constants.TYPE).toString()
                        .equals(Constants.bookingConfirmation)
                ) {
                    openStartRide(intent!!)

                } else if (intent?.getStringExtra(Constants.TYPE).toString()
                        .equals(Constants.rideCancelled)
                ) {
                    binding.frameRideStart.hide()
                    rideCancelled()
                }

            }


        }
    }


    var locationUpdateBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e(TAG, "onBroadCastReceiver before intent${intent}")
            //location update

            setUpLocationListener()


        }
    }


    private fun openStartRide(intent: Intent) {
        Log.e(TAG, "openStartRide $intent")
        if (intent.hasExtra(Constants.NAME)) {
            clientRiderName = intent.getStringExtra(Constants.NAME).toString().trim()
            clientRiderPhoto = intent.getStringExtra(Constants.PHOTO).toString().trim()
            clientRiderPrice = intent.getStringExtra(Constants.PRICE).toString().trim()
            clientRiderRideId = intent.getStringExtra(Constants.RIDEID).toString().trim()
            clientRiderPickupAddress =
                intent.getStringExtra(Constants.PICKUPADDFRESS).toString().trim()
            clientRiderDropOffAddress =
                intent.getStringExtra(Constants.DROPOFFADDRESS).toString().trim()


            binding.frameRideStart.show()

            openFragment(
                UserRideRequestFragment.newInstance(
                    clientRiderName,
                    clientRiderPhoto,
                    clientRiderPrice,
                    clientRiderRideId,
                    clientRiderPickupAddress,
                    clientRiderDropOffAddress, "0"
                )
            )

        } else {


            // Do something else
        }
    }


    private fun startJobSchedular() {
        jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val jobInfo = JobInfo.Builder(123, ComponentName(this, UpdateLocationService::class.java))
        val job = jobInfo.setRequiresCharging(false)
            .setMinimumLatency(1)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setOverrideDeadline(3 * 60 * 1000).build()

        jobScheduler.schedule(job)
    }

    private fun stopJobSchedular() {
        if (jobScheduler != null) {
            jobScheduler.cancelAll()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverMapScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        LocalBroadcastManager.getInstance(this).registerReceiver(
            notificationBroadcastReceiver,
            IntentFilter(getString(R.string.action_notification_foreground))
        )

        LocalBroadcastManager.getInstance(this).registerReceiver(
            locationUpdateBroadcastReceiver,
            IntentFilter(getString(R.string.location_update))
        )




        polylines = ArrayList()
        Log.e(TAG, "onCreate called")

        if (userPreferences.getPhoto().isNotEmpty())
            binding.ivProfile.loadImage(userPreferences.getPhoto())



        requestPermission()
        setUpLocationListener()

        if (intent.hasExtra(Constants.NAME)) {

            if (intent.getStringExtra(Constants.TYPE).toString()
                    .equals(Constants.bookingConfirmation)
            ) {
                openStartRide(intent)
            } else if (intent.getStringExtra(Constants.TYPE).toString()
                    .equals(Constants.rideCancelled)
            ) {
                rideCancelled()
            }


        } else {
            // Do something else
        }


        binding.map.onCreate(savedInstanceState)
        binding.map.onResume()
        try {
            MapsInitializer.initialize(this.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.map.getMapAsync(this)
        setupViewModel()

        if (userPreferences.getOnlineStatus().toString().trim().isNotEmpty()) {
            if (userPreferences.getOnlineStatus().equals(Constants.ONLINE)) {
                binding.swipeButton.isChecked = true
                binding.frameRideStart.show()
            } else {
                binding.swipeButton.isChecked = false
                binding.frameRideStart.hide()
            }
        }



        binding.swipeButton.onSwipedListener = {

        }

        binding.swipeButton.onSwipedOnListener = {
            userPreferences.saveOnlineStatus(Constants.ONLINE)
            onlineStatus("1")
            binding.frameNotification.hide()
            //getMyBookings()
            binding.frameRideStart.show()

        }

        binding.swipeButton.onSwipedOffListener = {
            userPreferences.saveOnlineStatus(Constants.OFFLINE)
            onlineStatus("0")
            binding.frameNotification.show()
            binding.frameRideStart.hide()
        }

    }

    /*val intent = Intent(Intent.ACTION_DIAL)
intent.data = Uri.parse("tel:<number>")
startActivity(intent)*/

    private fun onlineStatus(type: String) {
        viewModel.onlineStatusUpdate(
            userPreferences.getUserREf().trim(),
            type

        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        // dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {

                            } else {
                                setError(user.body()?.msg.toString())
                            }
                        }
                    }
                    Status.ERROR -> {
                        // dismissDialog()
                        setError(it.message.toString())

                    }
                    Status.LOADING -> {
                        //  showDialog()
                    }
                }
            }
        })
    }


    override fun openClose(type: Int) {

        Log.e(TAG, "openClose type $type")

        //0 means fragment open
        if (type == 0) {

            binding.tvOpenFragment.hide()
            binding.frameRideStart.show()


        } else {
            binding.tvOpenFragment.show()
            binding.frameRideStart.hide()
        }


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

                /*DriverProfileActivity*/

                ActivityStarter.of(DriverProfileActivity.getStartIntent(this))
                    .startFrom(this)

            }

            binding.ivNotification -> {
                /*           userPreferences.clearPrefs()
                           ActivityStarter.of(LoginActivity.getStartIntent(this))
                               .finishAffinity()
                               .startFrom(this)*/

            }

            binding.tvOpenFragment -> {
                openClose(0)


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
                                    binding.tvOpenFragment.hide()
                                    setError(user.body()?.msg.toString() + getString(R.string.ride_notified_msg))
                                } else {

                                    binding.frameRideStart.show()
                                    openFragment(
                                        UserRideRequestFragment.newInstance(
                                            rideInfo?.userName.toString().trim(),
                                            rideInfo?.userProfilePic.toString().trim(),
                                            rideInfo?.fareAmount.toString().trim(),
                                            rideInfo?.rideId.toString().trim(),
                                            rideInfo?.pickupName.toString().trim(),
                                            rideInfo?.dropOffName.toString().trim(), "0"
                                        )
                                    )

                                    binding.tvOpenFragment.hide()
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
        rideId: String,

        ) {

        //type 0 reject //type 1 accept
        if (type.equals("0")) {

            //cancel ride dialog


            cancelRideDialog(type.toString(), rideId)
        } else {
            acceptReject(type.toString(), rideId)
        }

    }

    override fun onStartTrip(rideId: String) {
        //taking routes
        //finding routes

        //start trip type 0
        binding.tvOpenFragment.text = getString(R.string.end_trip)
        startTrip(rideId.toString().trim())


    }

    override fun onEndTrip(rideId: String) {

        //end trip type 1
        //endTrip
        endTrip(rideId)

    }


    private fun cancelRideDialog(type: String, rideId: String) {
        erasePolylines()
        val builder =
            androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alert))
        builder.setMessage(getString(R.string.cancel_ride_msg))
        builder.setCancelable(true)
        builder.setPositiveButton(
            "Yes"
        ) { dialog, which ->
            acceptReject(type.toString(), rideId)

        }
        builder.setNegativeButton("Cancel")
        { dialog, which ->

            dialog.dismiss()


        }
        builder.show()
    }


    private fun rideCancelled() {
        val builder =
            androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alert))
        builder.setMessage("user has cancelled your ride")
        builder.setCancelable(true)
        builder.setPositiveButton(
            "Ok"
        ) { dialog, which ->
            dialog.dismiss()

        }

        builder.show()
    }


    private fun showRideCompletedDialog(rideId: String, userRef: String, userName: String) {
        val builder =
            androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alert))
        builder.setMessage(getString(R.string.add_review_message))
        builder.setCancelable(true)
        builder.setPositiveButton(
            "Yes"
        ) { dialog, which ->
            ActivityStarter.of(
                RateUserActivity.getStartIntent(
                    this,
                    rideId,
                    userRef,
                    userName
                )
            ).finishCurrentActivity()

                .startFrom(this)

        }
        builder.setNegativeButton("Cancel")
        { dialog, which ->

            dialog.dismiss()
            binding.frameRideStart.hide()

        }
        builder.show()
    }

    private fun endTrip(rideId: String) {
        binding.frameRideStart.hide()
        erasePolylines()
        viewModel.startTrip(
            rideId, "0","10"
        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {
                                //Nothing happen start routing
                                //open activity
                                showRideCompletedDialog(
                                    rideInfo?.rideId.toString(),
                                    rideInfo?.userRef.toString(),
                                    rideInfo?.userName.toString()
                                )


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

    private fun startTrip(rideId: String) {
        viewModel.startTrip(
            rideId, "1","10"

        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {

                    Status.SUCCESS -> {
                        dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {
                                //Nothing happen start routing

                                binding.tvOpenFragment.text = getString(R.string.end_your_trip)
                                startJobSchedular()

                                ///service and routing code should present


                                /*    var pickUpLat = rideInfo?.pickupLat.toString().trim().toDouble()
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
                                    findRoutes(startLatLong, endLatLong)*/


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


    //rideDistance code need to be added


    private fun acceptReject(
        type: String,
        rideId: String
    ) {
        //type 0 reject 1 accept
        viewModel.acceptRideByDriver(
            userPreferences.getUserREf().trim(),
            rideId.toString(),
            type, "10"

        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {

                                if (type.equals("0")) {
                                    getMyBookings()
                                    binding.tvOpenFragment.hide()
                                    binding.frameRideStart.hide()
                                } else if (type.equals("1")) {


                                    binding.tvOpenFragment.hide()
                                    binding.frameRideStart.show()
                                    rideInfo = user.body()?.rideInfo
                                    binding.tvOpenFragment.text =
                                        getString(R.string.start_your_trip)

                                    destinationLatitude= rideInfo?.dropOffLat.toString()
                                    destinationLogitude= rideInfo?.dropOffLog.toString()


                                    //here type 0 defines that you have started the ride after accepting it
                                    openFragment(
                                        StartRideFragment.newInstance(
                                            rideInfo?.userName.toString().trim(),
                                            rideInfo?.userProfilePic.toString().trim(),
                                            rideInfo?.fareAmount.toString().trim(),
                                            rideInfo?.rideId.toString().trim(),
                                            rideInfo?.pickupName.toString().trim(),
                                            rideInfo?.dropOffName.toString().trim(), "0"


                                        )


                                    )

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
                                    findRoutes(startLatLong, endLatLong)
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
        googleMap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.uber_style_map));


        /*   val latLng = LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())
           val markerOptions = MarkerOptions().position(latLng)
           googleMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
           googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
           googleMap!!.addMarker(markerOptions)*/
    }


/*find routes*/

    private fun findRoutes(startPoint: LatLng?, endPoint: LatLng?) {
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
    //

    private fun erasePolylines() {
        for (line in polylines!!) {
            line.remove()
        }
        polylines!!.clear()
        googleMap!!.clear()


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
                polyOptions.color(ContextCompat.getColor(this, R.color.dark_blue))
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

    private fun openFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_ride_start, fragment)
            commit()
        }




    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }


    private fun setUpLocationListener() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // for getting the current location update after every 2 seconds with high accuracy
        val locationRequest =
            LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

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
                    if (locationResult != null) {
                        for (location in locationResult.locations) {

                            val latLng = LatLng(location.latitude, location.longitude)

                            val markerOptions = MarkerOptions().position(latLng)
                            if (googleMap != null) {
                                googleMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                                googleMap!!.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        latLng,
                                        15f
                                    )
                                )
                                googleMap!!.addMarker(markerOptions)
                            }

                        }
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

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(notificationBroadcastReceiver)


        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(locationUpdateBroadcastReceiver)

    }
}