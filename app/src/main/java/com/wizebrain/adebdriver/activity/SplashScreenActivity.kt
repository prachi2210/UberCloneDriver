package com.wizebrain.adebdriver.activity

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
        moveToDestination()
    }

    private fun moveToDestination() {
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
        }, SPLASH_DISPLAY_LENGTH)


    }
}