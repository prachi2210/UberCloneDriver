package com.wizebrain.adebdriver.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.base.BaseActivity
import com.wizebrain.adebdriver.data.api.ApiHelper
import com.example.adebuser.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.ViewModelProviderFactory
import com.wizebrain.adebdriver.databinding.ActivityLoginBinding
import com.wizebrain.adebdriver.ui.auth.document_verification.DocumentActivity
import com.wizebrain.adebdriver.ui.auth.forgotpassword.ForgotPasswordActivity
import com.wizebrain.adebdriver.ui.map.DriverMapActivityScreen
import com.wizebrain.adebdriver.utils.ActivityStarter
import com.wizebrain.adebdriver.utils.Constants
import com.wizebrain.adebdriver.utils.Status

class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(AuthViewModel::class.java)
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

                    Constants.DEVICE_TOKEN
                ).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                dismissDialog()
                                resource.data?.let { user ->
                                    if (user.body()?.status.equals("success")) {
<<<<<<< HEAD
                                        userPreferences.saveUserID(user.body()?.UserInfo?.id)
                                        userPreferences.saveUserRef(user.body()?.UserInfo?.userRef)
                                        userPreferences.saveName(user.body()?.UserInfo?.name)
                                        userPreferences.savePhoto(user.body()?.UserInfo?.profilePic)
                                        userPreferences.savePhoneNumber(user.body()?.UserInfo?.phoneNumber)
                                        userPreferences.saveHealthReport(user.body()?.UserInfo?.healthReport)
                                        userPreferences.savePersonalID(user.body()?.UserInfo?.personalId)
                                        userPreferences.saveDriveLicense(user.body()?.UserInfo?.drivingLicense)

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

=======
                                        userPreferences.saveUserDetails(user.body()?.UserInfo)
//                                        userPreferences.saveUserID(user.body()?.UserInfo?.id)
//                                        userPreferences.saveUserRef(user.body()?.UserInfo?.userRef)
//                                        userPreferences.saveName(user.body()?.UserInfo?.name)
//                                        userPreferences.savePhoto(user.body()?.UserInfo?.profilePic)
//                                        userPreferences.savePhoneNumber(user.body()?.UserInfo?.phoneNumber)
                                        /*     ActivityStarter.of(HomeScreenActivity.getStartIntent(this))
                                                 .finishAffinity()
                                                 .startFrom(this)*/
>>>>>>> 03137f3dc1d59a4766cbb4c88bfe11acdbd2a2e0

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