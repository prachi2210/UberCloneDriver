package com.wizebrain.adebdriver.ui.profile.modules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.BaseFragment
import com.wizebrain.adebdriver.databinding.FragmentProfileSettingBinding
import com.wizebrain.adebdriver.databinding.FragmentProfileStatsBinding


class ProfileStatsFragment : BaseFragment() {


    private var _binding: FragmentProfileStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileStatsBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}