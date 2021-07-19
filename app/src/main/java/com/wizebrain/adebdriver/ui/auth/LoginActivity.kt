package com.wizebrain.adebdriver.ui.auth

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wizebrain.adebdriver.base.BaseActivity
import com.wizebrain.adebdriver.data.api.ApiHelper
import com.example.adebuser.data.api.RetrofitBuilder
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.ViewModelProviderFactory
import com.wizebrain.adebdriver.databinding.ActivityLoginBinding
import com.wizebrain.adebdriver.ui.auth.document_verification.DocumentActivity
import com.wizebrain.adebdriver.ui.auth.forgotpassword.ForgotPasswordActivity
import com.wizebrain.adebdriver.ui.map.DriverMapActivityScreen
import com.wizebrain.adebdriver.utils.ActivityStarter
import com.wizebrain.adebdriver.utils.PermissionUtils
import com.wizebrain.adebdriver.utils.Status

class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel
    private val LOCATION_REQUEST_CODE = 23

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermission()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(AuthViewModel::class.java)
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
            setUpLocationListener()
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

                            setUpLocationListener()
                        }
                        else -> {
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
                        val latLng = LatLng(location.latitude, location.longitude)
                      //  userPreferences.getDriveLicense()
                        userPreferences.saveCurrentLatitude(location.latitude.toString())
                        userPreferences.saveCurrentLongitude(location.longitude.toString())
                    }
                }
            },
            Looper.myLooper()
        )

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.tvLogin -> {
                login()
            }

            binding.tvRegister -> {
                ActivityStarter.of(SignUpActivity.getStartIntent(this))
                    .startFrom(this)
            }

            binding.tvForgotPassword -> {
                ActivityStarter.of(ForgotPasswordActivity.getStartIntent(this))
                    .startFrom(this)
            }


        }
    }


    private fun login() {

        when {
            checkEmpty(binding.etMobile) -> {
                setError(getString(R.string.mobile_error))
            }

            checkEmpty(binding.etPassword) -> {
                setError(getString(R.string.password_error))
            }

            else -> {
                viewModel.login(
                    binding.etMobile.text.toString().trim(),
                    binding.etPassword.text.toString().trim(),
                    userPreferences.getDeviceToken().trim(),
                    userPreferences.getLatitude().trim(),
                    userPreferences.getLongitude().trim()
                ).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                dismissDialog()
                                resource.data?.let { user ->
                                    if (user.body()?.status.equals("success")) {
                                        userPreferences.saveUserID(user.body()?.UserInfo?.id)
                                        userPreferences.saveUserRef(user.body()?.UserInfo?.userRef)
                                        userPreferences.saveName(user.body()?.UserInfo?.name)
                                        userPreferences.savePhoneNumber(user.body()?.UserInfo?.phoneNumber)
                                        userPreferences.saveHealthReport(user.body()?.UserInfo?.healthReport)
                                        userPreferences.savePersonalID(user.body()?.UserInfo?.personalId)
                                        userPreferences.saveDriveLicense(user.body()?.UserInfo?.drivingLicense)
                                        userPreferences.savePhoto(RetrofitBuilder.BASE_URL + user.body()?.UserInfo?.profilePic)

                                        if (user.body()?.UserInfo?.healthReport.equals("") ||
                                            user.body()?.UserInfo?.personalId.equals("")
                                            || user.body()?.UserInfo?.drivingLicense.equals("")
                                        ) {
                                            ActivityStarter.of(DocumentActivity.getStartIntent(this))
                                                .finishAffinity()
                                                .startFrom(this)

                                        } else {
                                            ActivityStarter.of(
                                                DriverMapActivityScreen.getStartIntent(
                                                    this
                                                )
                                            )
                                                .finishAffinity()
                                                .startFrom(this)
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
        }

    }


}