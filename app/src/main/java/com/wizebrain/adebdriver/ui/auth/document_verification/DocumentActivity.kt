package com.wizebrain.adebdriver.ui.auth.document_verification

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.wizebrain.adebdriver.base.BaseActivity
import com.wizebrain.adebdriver.databinding.ActivityDocumentBinding
import com.wizebrain.adebdriver.extensions.show
import com.wizebrain.adebdriver.ui.auth.LoginActivity
import com.wizebrain.adebdriver.utils.ActivityStarter
import com.wizebrain.adebdriver.utils.Constants

class DocumentActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDocumentBinding
    private val TAG: String = DocumentActivity::class.java.simpleName

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


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.e(TAG, "OnNewIntentCalled")
        Log.e(TAG, "Constants.TYPE   ${intent?.getStringExtra(Constants.TYPE).toString().trim()}")


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e(TAG, "OnCreateCalled")


            if (userPreferences.getDriveLicense().isNotEmpty())
                binding.ivDrivingLicense.show()
            if (userPreferences.getPersonalID().isNotEmpty())
                binding.ivPersonalId.show()
            if (userPreferences.getHealthReport().isNotEmpty())
                binding.ivHealthCard.show()


    }

    override fun onClick(v: View?) {
        when (v) {

            binding.linearDrivingLicense -> {
                if (userPreferences.getDriveLicense().equals("")) {
                    ActivityStarter.of(DrivingLicenseActivity.getStartIntent(this))
                        .requestCode(1)
                        .startFrom(this)
                }


            }

            binding.linearPersonalIdCard -> {

                if (userPreferences.getPersonalID().equals("")) {
                    ActivityStarter.of(PersonalIdActivity.getStartIntent(this))
                        .requestCode(2)
                        .startFrom(this)
                }


            }

            binding.linearHealthCard -> {
                if (userPreferences.getHealthReport().equals("")) {
                    ActivityStarter.of(HealthReportActivity.getStartIntent(this))
                        .requestCode(3)
                        .startFrom(this)
                }
            }

            binding.tvProceed -> {
                if (userPreferences.getDriveLicense()
                        .isNotEmpty() && userPreferences.getHealthReport()
                        .isNotEmpty() && userPreferences.getPersonalID().isNotEmpty()
                ) {
                    ActivityStarter.of(LoginActivity.getStartIntent(this))
                        .finishAffinity()
                        .startFrom(this)
                } else {
                    showToast(this, "Please verify your all the documents first")
                }


            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            if (data?.getStringExtra(Constants.TYPE) != null) {

                when (data.getStringExtra(Constants.TYPE).toString().trim()) {
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
    }

}