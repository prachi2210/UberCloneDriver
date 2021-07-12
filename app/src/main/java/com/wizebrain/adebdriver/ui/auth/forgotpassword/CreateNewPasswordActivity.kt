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
import com.wizebrain.adebdriver.databinding.ActivityForgotPasswordChangeBinding
import com.wizebrain.adebdriver.ui.auth.AuthViewModel
import com.wizebrain.adebdriver.ui.auth.SuccessfulScreenActivity
import com.wizebrain.adebdriver.utils.ActivityStarter
import com.wizebrain.adebdriver.utils.Constants
import com.wizebrain.adebdriver.utils.Status

class CreateNewPasswordActivity : BaseActivity(), View.OnClickListener {

    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: ActivityForgotPasswordChangeBinding

    companion object {
        fun getStartIntent(context: Context, phoneNumber: String?): Intent {
            val startIntent = Intent(context, CreateNewPasswordActivity::class.java)
            startIntent.putExtra(Constants.PHONENUMBER, phoneNumber)
            return startIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordChangeBinding.inflate(layoutInflater)
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
            binding.btnDone -> {
                createNewPassword()
            }

            binding.includeActionBar.ivBack -> {
                onBackPressed()
            }
        }
    }


    private fun createNewPassword() {
        when {
            checkEmpty(binding.etPassword) -> {
                setError(getString(R.string.password_error))
            }

            checkEmpty(binding.etConfirmPassword) -> {
                setError(getString(R.string.confirm_password_error))
            }

            (binding.etPassword.text.toString().trim() != binding.etConfirmPassword.text.toString()
                .trim()) -> {
                setError(getString(R.string.password_match_error))

            }

            else -> {

                viewModel.createNewPassword(
                    intent.getStringExtra(Constants.PHONENUMBER).toString(),
                    binding.etPassword.text.toString().trim()

                ).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                dismissDialog()
                                resource.data?.let { user ->
                                    if (user.body()?.status.equals("success")) {
                                        ActivityStarter.of(
                                            SuccessfulScreenActivity.getStartIntent(
                                                this
                                            )
                                        )
                                            .finishCurrentActivity()
                                            .startFrom(this)
                                        showToast(this, user.body()?.msg.toString())
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