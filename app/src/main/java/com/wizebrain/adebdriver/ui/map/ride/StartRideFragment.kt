package com.wizebrain.adebdriver.ui.map.ride

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adebuser.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.base.BaseFragment
import com.wizebrain.adebdriver.databinding.FragmentStartRideBinding
import com.wizebrain.adebdriver.extensions.loadImage
import com.wizebrain.adebdriver.ui.map.response.RideInfo
import com.wizebrain.adebdriver.ui.map.ride.listener.UserRideListener
import com.wizebrain.adebdriver.utils.Constants


class StartRideFragment : BaseFragment(), View.OnClickListener {
    private var type: String? = null
    private val binding get() = _binding!!
    private var _binding: FragmentStartRideBinding? = null
    private var rideInfo: RideInfo? = null
    private var callback: UserRideListener? = null
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            rideInfo = it.getParcelable(Constants.RIDEINFO)
            type = it.getString(Constants.TYPE)
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as UserRideListener

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
        binding.tvName.text = rideInfo?.userName.toString().trim()
        binding.tvPrice.text = "$ ${rideInfo?.fareAmount}"
        binding.tvPaymentType.text = rideInfo?.paymentMode.toString().trim()
        if (rideInfo?.userProfilePic.toString().trim().isNotEmpty()) {
            binding.ivProfile.loadImage(RetrofitBuilder.BASE_URL + rideInfo?.userProfilePic)
        }
        binding.toAddress.text = rideInfo?.dropOffName?.trim()
        binding.tvFromAddress.text = rideInfo?.pickupName?.trim()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onClick(v: View?) {
        when (v) {
            binding.tvRideStatus -> {
                startTrip()
            }


        }

    }

    private fun startTrip() {
        when (type) {
            "0" -> {
                callback?.onEndTrip(rideInfo)
            }

            else -> {
                type = "0"
                callback?.onStartTrip(rideInfo)
                binding.tvRideStatus.text = "End trip"
                binding.tvCloseFragment.text = "Ride Start"
            }
        }
        
    }


    companion object {
        @JvmStatic
        fun newInstance(rideInfo: RideInfo?, type: String) =
            StartRideFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constants.RIDEINFO, rideInfo)
                    putString(Constants.TYPE, type)
                }
            }
    }
}