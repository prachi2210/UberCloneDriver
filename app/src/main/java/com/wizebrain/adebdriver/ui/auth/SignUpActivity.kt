package com.wizebrain.adebdriver.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wizebrain.adebdriver.base.BaseActivity
import com.wizebrain.adebdriver.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.ViewModelProviderFactory
import com.wizebrain.adebdriver.data.api.ApiHelper
import com.wizebrain.adebdriver.databinding.ActivitySignUpBinding
import com.wizebrain.adebdriver.ui.auth.document_verification.DocumentActivity
import com.wizebrain.adebdriver.utils.ActivityStarter
import com.wizebrain.adebdriver.utils.Status

class SignUpActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: AuthViewModel

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(AuthViewModel::class.java)
    }


    private fun signUp() {

        when {

            checkEmpty(binding.etName) -> {
                setError(getString(R.string.name_error))
            }

            checkEmpty(binding.etEmail) -> {
                setError(getString(R.string.email_empty_error))
            }

            !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString().trim())
                .matches() -> {
                setError(getString(R.string.invalid_email_error))
            }
            checkEmpty(binding.etMobile) -> {
                setError(getString(R.string.mobile_error))
            }

            checkEmpty(binding.etPassword) -> {
                setError(getString(R.string.password_error))
            }

            checkEmpty(binding.etUniqueNo) -> {
                setError(getString(R.string.unique_number_error))
            }

            checkEmpty(binding.etDrivingExp) -> {
                setError(getString(R.string.driving_exp_error))
            }

            !binding.cb.isChecked -> {
                setError(getString(R.string.privacy_policy_validation))
            }


            else -> {
                viewModel.signUp(
                    binding.etName.text.toString().trim(),
                    binding.etEmail.text.toString().trim(),
                    binding.etMobile.text.toString().trim(),
                    binding.etPassword.text.toString().trim(),
                    binding.etUniqueNo.text.toString().trim(),
                    binding.etDrivingExp.text.toString().trim(),
                    userPreferences.getDeviceToken().trim()
                ).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                dismissDialog()
                                resource.data?.let { user ->
                                    if (user.body()?.status.equals("success")) {
                                        ActivityStarter.of(DocumentActivity.getStartIntent(this))
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


    }

    override fun onClick(v: View?) {
        when (v) {
            binding.loginBtn -> {
                signUp()
            }
        }
    }
}