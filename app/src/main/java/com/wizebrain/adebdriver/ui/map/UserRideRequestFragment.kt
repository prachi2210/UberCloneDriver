package com.wizebrain.adebdriver.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.BaseFragment
import com.wizebrain.adebdriver.databinding.FragmentUserRideRequestBinding


class UserRideRequestFragment : BaseFragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentUserRideRequestBinding? = null

    private val userRideRequestAdapter: UserRideRequestAdapter by lazy {
        UserRideRequestAdapter(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserRideRequestBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvUserRequests.adapter = userRideRequestAdapter


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}