package com.wizebrain.adebdriver.ui.map.ride

import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adebuser.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.base.BaseFragment
import com.wizebrain.adebdriver.databinding.FragmentUserRideDetailsBinding
import com.wizebrain.adebdriver.extensions.loadImage
import com.wizebrain.adebdriver.ui.map.response.RideInfo
import com.wizebrain.adebdriver.utils.Constants


class UserRideDetailsFragment : BaseFragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentUserRideDetailsBinding? = null
    private var userName: String? = null
    private var id: String? = null
    private var userProfilePic: String? = null
    private var fareAmount: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserRideDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val rideInfo: RideInfo? = it.getParcelable(Constants.RIDEINFO)
            userName = rideInfo?.userName
            id = rideInfo?.rideId
            userProfilePic = rideInfo?.userProfilePic
            fareAmount = rideInfo?.fareAmount
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvName.text = userName
        binding.tvPrice.text = fareAmount
        if (userProfilePic.toString().trim().isNotEmpty()) {
            binding.ivProfile.loadImage(RetrofitBuilder.BASE_URL + userProfilePic)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        @JvmStatic
        fun newInstance(rideInfo: RideInfo?) =
            UserRideDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constants.RIDEINFO, rideInfo)
                }
            }
    }







}