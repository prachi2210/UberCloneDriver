package com.wizebrain.adebdriver.ui.map.ride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.BaseFragment
import com.wizebrain.adebdriver.databinding.FragmentStartRideBinding
import com.wizebrain.adebdriver.databinding.FragmentUserRideDetailsBinding
import com.wizebrain.adebdriver.ui.auth.SignUpActivity
import com.wizebrain.adebdriver.ui.auth.forgotpassword.ForgotPasswordActivity
import com.wizebrain.adebdriver.utils.ActivityStarter


class StartRideFragment : BaseFragment(), View.OnClickListener {
    private var type: String? = null
    private val binding get() = _binding!!
    private var _binding: FragmentStartRideBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString("type")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartRideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRideStatus.setOnClickListener(this)

    //Ride start



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.tvRideStatus -> {


                //login()
            }


        }

    }

}