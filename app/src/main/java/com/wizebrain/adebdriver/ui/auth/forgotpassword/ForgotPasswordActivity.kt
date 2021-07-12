package com.wizebrain.adebdriver.ui.auth.forgotpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.base.BaseActivity
import com.example.adebuser.data.api.ApiHelper
import com.example.adebuser.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.ViewModelProviderFactory
import com.wizebrain.adebdriver.databinding.ActivityForgotPasswordBinding
import com.wizebrain.adebdriver.ui.auth.AuthViewModel
import com.wizebrain.adebdriver.utils.ActivityStarter
import com.wizebrain.adebdriver.utils.Status


class ForgotPasswordActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var viewModel: AuthViewModel

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ForgotPasswordActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
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
            binding.btnRequest -> {
                requestOtp()
            }

            binding.includeActionBar.ivBack -> {
                onBackPressed()
            }


        }
    }


    private fun requestOtp() {
        when {
            checkEmpty(binding.etMobile) -> {
                setError(getString(R.string.mobile_error))
            }
            else -> {
                viewModel.forgotPassword(
                    binding.etMobile.text.toString().trim()

                ).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                dismissDialog()
                                resource.data?.let { user ->
                                    if (user.body()?.status.equals("success")) {
                                       // setError(user.body()?.msg.toString())
                                        ActivityStarter.of(
                                            ForgotPasswordOtpActivity.getStartIntent(
                                                this,
                                                binding.etMobile.text.toString().trim(),
                                                user.body()?.otp.toString().trim()
                                            )
                                        ).startFrom(this)


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