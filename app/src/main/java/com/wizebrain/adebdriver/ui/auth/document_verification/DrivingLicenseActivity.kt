package com.wizebrain.adebdriver.ui.auth.document_verification

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.base.BaseActivity
import com.example.adebuser.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.ViewModelProviderFactory
import com.wizebrain.adebdriver.data.api.ApiHelper
import com.wizebrain.adebdriver.databinding.ActivityDocumentBinding
import com.wizebrain.adebdriver.databinding.ActivityDrivingLicenseBinding
import com.wizebrain.adebdriver.ui.auth.AuthViewModel
import com.wizebrain.adebdriver.ui.auth.LoginActivity
import com.wizebrain.adebdriver.utils.Constants
import com.wizebrain.adebdriver.utils.Status

class DrivingLicenseActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDrivingLicenseBinding
    private lateinit var viewModel: AuthViewModel


    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DrivingLicenseActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrivingLicenseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(AuthViewModel::class.java)
    }

    private fun uploadDrivingLicense() {
        viewModel.uploadDrivingLicense(
            userPreferences.getUserDetails()?.userRef.toString(), "", "", null, null
        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {

                                /*     ActivityStarter.of(HomeScreenActivity.getStartIntent(this))
                                         .finishAffinity()
                                         .startFrom(this)*/

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

    override fun onClick(v: View?) {

    }

}

