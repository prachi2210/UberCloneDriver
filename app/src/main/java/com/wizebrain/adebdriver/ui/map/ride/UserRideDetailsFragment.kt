package com.wizebrain.adebdriver.ui.map.ride

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.BaseFragment
import com.wizebrain.adebdriver.databinding.FragmentUserRideDetailsBinding
import com.wizebrain.adebdriver.databinding.FragmentUserRideRequestBinding
import com.wizebrain.adebdriver.ui.map.ride.listener.UserRideAcceptRejectListener


class UserRideDetailsFragment() : BaseFragment() {


    private val binding get() = _binding!!
    private var _binding: FragmentUserRideDetailsBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserRideDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}