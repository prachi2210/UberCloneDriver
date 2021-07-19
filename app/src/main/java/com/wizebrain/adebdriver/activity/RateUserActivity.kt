package com.wizebrain.adebdriver.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wizebrain.adebdriver.base.BaseActivity
import com.example.adebuser.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.ViewModelProviderFactory
import com.wizebrain.adebdriver.data.api.ApiHelper
import com.wizebrain.adebdriver.databinding.ActivityRateUserBinding
import com.wizebrain.adebdriver.ui.map.MapViewModel
import com.wizebrain.adebdriver.utils.Constants
import com.wizebrain.adebdriver.utils.Status

class RateUserActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRateUserBinding
    private lateinit var viewModel: MapViewModel

    companion object {
        fun getStartIntent(
            context: Context,
            rideId: String,
            userRef: String,
            userName: String
        ): Intent {
            val startIntent = Intent(context, RateUserActivity::class.java)
            startIntent.putExtra(Constants.RIDE_ID, rideId)
            startIntent.putExtra(Constants.USER_REF, userRef)
            startIntent.putExtra(Constants.USER_NAME, userName)
            return startIntent
        }
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MapViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRateUserBinding.inflate(layoutInflater)
        binding.tvDriverName.text = intent.getStringExtra(Constants.USER_NAME)
        setContentView(binding.root)
        setupViewModel()
    }

    private fun giveRatingToUser() {
        when {
            checkEmpty(binding.etComment) -> {
                setError(getString(R.string.feedback_validation))
            }


            else -> {
                viewModel.addRating(
                    intent.getStringExtra(Constants.RIDE_ID).toString(),
                    userPreferences.getUserREf(),
                    intent.getStringExtra(Constants.USER_REF).toString(),
                    "3",
                    binding.etComment.toString().trim(),
                ).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                dismissDialog()
                                resource.data?.let { user ->
                                    if (user.body()?.status.equals("success")) {
                                        showToast(this, "Thanks for the feedback")

                                    } else {
                                        setError(user.body()?.msg.toString())
                                    }
                                }
                            }
                            Status.ERROR -> {
                                dismissDialog()
                                // setError(it.message.toString())
                                showToast(this, "Thanks for the feedback")


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
            binding.btnRate -> {
                giveRatingToUser()
            }
        }
    }
}