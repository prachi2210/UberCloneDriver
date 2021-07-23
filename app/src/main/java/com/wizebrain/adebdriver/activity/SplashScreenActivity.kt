package com.wizebrain.adebdriver.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.wizebrain.adebdriver.base.BaseActivity
import com.wizebrain.adebdriver.databinding.ActivitySplashBinding
import com.wizebrain.adebdriver.ui.auth.LoginActivity
import com.wizebrain.adebdriver.ui.auth.document_verification.DocumentActivity
import com.wizebrain.adebdriver.ui.auth.document_verification.DrivingLicenseActivity
import com.wizebrain.adebdriver.ui.map.DriverMapActivityScreen
import com.wizebrain.adebdriver.utils.ActivityStarter
import com.wizebrain.adebdriver.utils.Constants

class SplashScreenActivity : BaseActivity() {
    private val TAG: String = SplashScreenActivity::class.java.simpleName
    private lateinit var binding: ActivitySplashBinding

    companion object {
        var SPLASH_DISPLAY_LENGTH: Long = 1600
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e(TAG, "userRef ${userPreferences.getUserREf()}")

        checkBundle()
        moveToDestination()


    }

    private fun checkBundle() {

    }

    private fun moveToDestination() {


        /**/


        val bundle = intent.extras
        if (bundle != null) {

            if (bundle.getString(Constants.TYPE) != null) {
                Log.e(TAG, "getType ${bundle.getString(Constants.TYPE)}")
            }


            for (key in bundle.keySet()) {
                val value = intent.extras!!.get(key)
                Log.d(TAG, "Key: $key Value: $value")
            }

            if (bundle.getString("type").equals(Constants.bookingConfirmation)) {

                val intent = Intent(this, DriverMapActivityScreen::class.java)
                intent.putExtra(Constants.NAME, bundle.getString("name"))
                intent.putExtra(Constants.PHOTO, bundle.getString("photo"))
                intent.putExtra(Constants.PRICE, bundle.getString("price"))
                intent.putExtra(Constants.RIDEID, bundle.getString("rideid"))
                intent.putExtra(Constants.PICKUPADDFRESS, bundle.getString("pickupAddress"))
                intent.putExtra(Constants.DROPOFFADDRESS, bundle.getString("dropOffAddress"))
                startActivity(intent)
                finish()

            } else if (!bundle.getString("notifyType").isNullOrEmpty()) {
                if (bundle.getString("notifyType").equals(Constants.rideCancelled)) {
                    val intent = Intent(this, DriverMapActivityScreen::class.java)
                    intent.putExtra(Constants.TYPE, bundle.getString("notifyType"))
                    intent.putExtra(Constants.NAME, bundle.getString("userName"))
                    intent.putExtra(Constants.PHONENUMBER, bundle.getString("userPhoneNumber"))
                    intent.putExtra(Constants.RATING, bundle.getString("userRating"))
                    startActivity(intent)
                    finish()
                }
            } else {
                val intent = Intent(this, DriverMapActivityScreen::class.java)
                startActivity(intent)
                finish()

            }

        } else {
            Handler(Looper.getMainLooper()).postDelayed({

                when {
                    userPreferences.getUserId().equals("") -> {
                        ActivityStarter.of(LoginActivity.getStartIntent(this))
                            .finishAffinity()
                            .startFrom(this)
                    }
                    (userPreferences.getHealthReport() == "" ||
                            userPreferences.getPersonalID() == ""
                            || userPreferences.getDriveLicense() == ""
                            ) -> {
                        ActivityStarter.of(DocumentActivity.getStartIntent(this))
                            .finishAffinity()
                            .startFrom(this)

                    }
                    else -> {
                        ActivityStarter.of(DriverMapActivityScreen.getStartIntent(this))
                            .finishAffinity()
                            .startFrom(this)


                    }


                }
                Log.e("UserReference", "extras " + bundle)

            }, SPLASH_DISPLAY_LENGTH)
        }


    }
}