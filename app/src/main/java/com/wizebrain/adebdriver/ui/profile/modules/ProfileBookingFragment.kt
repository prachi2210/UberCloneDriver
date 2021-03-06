package com.wizebrain.adebdriver.ui.profile.modules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.BaseFragment
import com.wizebrain.adebdriver.databinding.FragmentProfileBookingBinding
import com.wizebrain.adebdriver.ui.profile.modules.adapter.ProfileBookingAdapter


class ProfileBookingFragment : BaseFragment() {
    private var _binding: FragmentProfileBookingBinding? = null
    private val binding get() = _binding!!


    private val profileBookingAdapter: ProfileBookingAdapter by lazy {
        ProfileBookingAdapter(requireActivity())
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBookingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTransactions.adapter=profileBookingAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}