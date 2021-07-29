package com.wizebrain.adebdriver.ui.profile

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.BaseActivity
import com.wizebrain.adebdriver.base.ViewModelProviderFactory
import com.wizebrain.adebdriver.data.api.ApiHelper
import com.wizebrain.adebdriver.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.databinding.ActivityDriverProfileBinding
import com.wizebrain.adebdriver.extensions.hide
import com.wizebrain.adebdriver.extensions.loadImage
import com.wizebrain.adebdriver.extensions.show
import com.wizebrain.adebdriver.ui.auth.AuthViewModel
import com.wizebrain.adebdriver.ui.auth.LoginActivity
import com.wizebrain.adebdriver.ui.auth.document_verification.DocumentActivity
import com.wizebrain.adebdriver.ui.map.DriverMapActivityScreen
import com.wizebrain.adebdriver.ui.map.MapViewModel
import com.wizebrain.adebdriver.ui.profile.modules.*
import com.wizebrain.adebdriver.utils.ActivityStarter
import com.wizebrain.adebdriver.utils.Status

class DriverProfileActivity : BaseActivity(), ProfileAdapter.ProfileViewListener,
    View.OnClickListener {
    private var modulesArray = arrayOf<String>()
    lateinit var drawableicons: TypedArray
    private lateinit var binding: ActivityDriverProfileBinding
    private lateinit var viewModel: AuthViewModel

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DriverProfileActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        profileActivityCallback(0)
        modulesArray = resources.getStringArray(R.array.driver_module_names)
        drawableicons = resources.obtainTypedArray(R.array.driver_module_drawable_icons);
        val profileAdapter: ProfileAdapter by lazy {
            ProfileAdapter(this, this, modulesArray, drawableicons)
        }git

        if (userPreferences.getPhoto().isNotEmpty())
            binding.imageDriver.loadImage(userPreferences.getPhoto())

       binding.driverName.text= userPreferences.getName()
        binding.tvMobileNo.text= userPreferences.getPhoneNumber()
        binding.rvProfile.adapter = profileAdapter
        binding.header.ivBack.setOnClickListener(this)
        binding.header.ivLogout.setOnClickListener(this)

        //bloodGroupList = resources.getStringArray(R.array.blood_group)
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(AuthViewModel::class.java)
    }


    private fun driverLogoutDialog() {
        val builder =
            androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.logout))
        builder.setMessage(getString(R.string.logout_confirm))
        builder.setCancelable(true)
        builder.setPositiveButton(
            "Yes"
        ) {


                dialog, which ->
            logout()

        }
        builder.setNegativeButton("Cancel")
        { dialog, which ->

            dialog.dismiss()


        }
        builder.show()
    }


    override fun profileActivityCallback(position: Int) {
        //
        when (position) {
            0 -> {
                openFragment(ProfileBookingFragment())



            }
            1 -> {
                openFragment(ProfileStatsFragment())

            }
            2 -> {
                openFragment(ProfileDocumentFragment())

            }
            3 -> {
                openFragment(ProfilePaymentFragment())

            }
            4 -> {
                openFragment(ProfileSupportFragment())

            }
            5 -> {
                openFragment(ProfileSettingFragment())

            }


        }
    }





    private fun openFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_layout, fragment)
            commit()
        }


    override fun onClick(v: View?) {
        when (v) {

            //
            binding.header.ivBack -> {
                onBackPressed()
            }

            binding.header.ivLogout -> {
                driverLogoutDialog()
            }


        }
    }


    private fun logout() {

        viewModel.logout(
            userPreferences.getUserREf()
        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {
                                userPreferences.clearPrefs()
                                ActivityStarter.of(LoginActivity.getStartIntent(this))
                                    .finishAffinity()
                                    .startFrom(this)


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