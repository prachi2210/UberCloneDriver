package com.wizebrain.adebdriver.ui.auth.forgotpassword

import `in`.aabhasjindal.otptextview.OTPListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.base.BaseActivity
import com.wizebrain.adebdriver.data.api.ApiHelper
import com.example.adebuser.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.base.ViewModelProviderFactory
import com.wizebrain.adebdriver.databinding.ActivityForgotPasswordOtpBinding
import com.wizebrain.adebdriver.ui.auth.AuthViewModel
import com.wizebrain.adebdriver.utils.ActivityStarter
import com.wizebrain.adebdriver.utils.Constants
import com.wizebrain.adebdriver.utils.Status

import java.text.DecimalFormat

class ForgotPasswordOtpActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityForgotPasswordOtpBinding
    private lateinit var viewModel: AuthViewModel
    var otpString = ""

    private val countDownTimer: CountDownTimer by lazy {
        object : CountDownTimer(120000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                var numberFormat = DecimalFormat("00");
                var min = (millisUntilFinished / 60000) % 60;
                var sec = (millisUntilFinished / 1000) % 60;
                binding.tvResendTimer.text =
                    numberFormat.format(min) + ":" + numberFormat.format(sec);
            }

            override fun onFinish() {
                binding.tvResendTimer.text = "00:00"
                resendOtp()
            }
    }

}



    companion object {
        fun getStartIntent(context: Context, phoneNumber: String, otp: String): Intent {
            val startIntent = Intent(context, ForgotPasswordOtpActivity::class.java)
            startIntent.putExtra(Constants.PHONENUMBER, phoneNumber)
            startIntent.putExtra(Constants.OTP, otp)
            return startIntent

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        countDownTimer.start()
        binding.tvMobileNo.text = intent.getStringExtra(Constants.PHONENUMBER)
        otpString = intent.getStringExtra(Constants.OTP).toString()
        binding.otp.setOTP(otpString)
        binding.otp.otpListener = object : OTPListener {
            override fun onInteractionListener() {
            }

            override fun onOTPComplete(otp: String) {
                otpString = otp
            }
        }
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(AuthViewModel::class.java)
    }


    override fun onClick(v: View?) {
        when (v) {
            binding.btnVerify -> {
                if (otpString.equals("")) {
                    setError("OTP field is Empty")
                    return
                }
                otpVerify()


            }

            binding.includeActionBar.ivBack -> {

                if (countDownTimer != null)
                    countDownTimer.cancel()

                onBackPressed()
            }
            binding.tvEdit -> {
                if (countDownTimer != null)
                    countDownTimer.cancel()

                onBackPressed()
            }


        }
    }


    private fun resendOtp() {
        viewModel.resendOtp(
            intent.getStringExtra(Constants.PHONENUMBER).toString().trim(),
        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {
                                otpString = user.body()?.otp.toString()
                                binding.otp.setOTP(otpString)
                                countDownTimer.start()

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


    private fun otpVerify() {
        viewModel.otpVerify(
            intent.getStringExtra(Constants.PHONENUMBER).toString().trim(),
            otpString.trim()
        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissDialog()
                        resource.data?.let { user ->
                            if (user.body()?.status.equals("success")) {
                                if (countDownTimer != null)
                                    countDownTimer.cancel()
                                ActivityStarter.of(
                                    CreateNewPasswordActivity.getStartIntent(
                                        this, intent.getStringExtra(Constants.PHONENUMBER)
                                    )
                                ).finishCurrentActivity()
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

