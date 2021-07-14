package com.wizebrain.adebdriver.ui.auth.document_verification

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.adebuser.base.BaseActivity
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.databinding.ActivityDocumentBinding
import com.wizebrain.adebdriver.databinding.ActivityLoginBinding
import com.wizebrain.adebdriver.extensions.show
import com.wizebrain.adebdriver.ui.auth.SignUpActivity
import com.wizebrain.adebdriver.ui.auth.forgotpassword.ForgotPasswordOtpActivity
import com.wizebrain.adebdriver.ui.map.DriverMapActivityScreen
import com.wizebrain.adebdriver.utils.ActivityStarter
import com.wizebrain.adebdriver.utils.Constants

class DocumentActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDocumentBinding

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DocumentActivity::class.java)
        }

        fun getStartIntent(context: Context, type: String): Intent {
            val startIntent = Intent(context, DocumentActivity::class.java)
            startIntent.putExtra(Constants.TYPE, type)
            return startIntent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.getStringExtra(Constants.TYPE) != null) {
            when (intent.getStringExtra(Constants.TYPE).toString().trim()) {
                "health" -> {
                    binding.ivHealthCard.show()
                }
                "personal" -> {
                    binding.ivPersonalId.show()
                }
                "license" -> {
                    binding.ivDrivingLicense.show()
                }
            }
        }

    }

    override fun onClick(v: View?) {
        when (v) {

            binding.tvDrivingLicense -> {
                if (userPreferences.getDriveLicense().equals("")) {
                    ActivityStarter.of(DrivingLicenseActivity.getStartIntent(this))
                        .startFrom(this)
                }

            }

            binding.tvPersonalIdCard -> {

                if (userPreferences.getPersonalID().equals("")) {
                    ActivityStarter.of(PersonalIdActivity.getStartIntent(this))
                        .startFrom(this)
                }


            }

            binding.tvHealthCard -> {
                if (userPreferences.getHealthReport().equals("")) {
                    ActivityStarter.of(HealthReportActivity.getStartIntent(this))
                        .startFrom(this)
                }
            }

            binding.tvProceed -> {
                if (userPreferences.getDriveLicense()
                        .isNotEmpty() && userPreferences.getHealthReport()
                        .isNotEmpty() && userPreferences.getPersonalID().isNotEmpty()
                ) {

                    ActivityStarter.of(DriverMapActivityScreen.getStartIntent(this))
                        .finishAffinity()
                        .startFrom(this)
                } else {

                    showToast(this, "Please verify your documents first")
                }


            }

        }

    }

}