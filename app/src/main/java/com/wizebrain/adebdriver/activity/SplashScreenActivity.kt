package com.wizebrain.adebdriver.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.adebuser.base.BaseActivity
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.databinding.ActivityLoginBinding
import com.wizebrain.adebdriver.databinding.ActivitySplashBinding
import com.wizebrain.adebdriver.ui.auth.LoginActivity
import com.wizebrain.adebdriver.ui.auth.SignUpActivity
import com.wizebrain.adebdriver.ui.auth.document_verification.DrivingLicenseActivity
import com.wizebrain.adebdriver.ui.auth.document_verification.PersonalIdActivity
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
        moveToDestination()
    }


    private fun moveToDestination() {
        Handler(Looper.getMainLooper()).postDelayed({
            when {
                userPreferences.getUserId().equals("") -> {
                    ActivityStarter.of(PersonalIdActivity.getStartIntent(this))
                        .finishAffinity()
                        .startFrom(this)
                }

                else -> {

                    //home screem would come intead of login
                    ActivityStarter.of(PersonalIdActivity.getStartIntent(this))
                        .finishAffinity()
                        .startFrom(this)


                }





            }
        }, SPLASH_DISPLAY_LENGTH)


    }
}