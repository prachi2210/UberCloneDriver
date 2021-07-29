package com.wizebrain.adebdriver.ui.profile.modules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.BaseFragment
import com.wizebrain.adebdriver.databinding.FragmentProfileStatsBinding
import com.wizebrain.adebdriver.ui.profile.modules.adapter.ProgressAdapter


class ProfileStatsFragment : BaseFragment() {


    private var _binding: FragmentProfileStatsBinding? = null
    private val binding get() = _binding!!
    private var monthArray = arrayOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileStatsBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        monthArray = resources.getStringArray(R.array.month_array)
         val progressBar: ProgressAdapter by lazy {
            ProgressAdapter(requireActivity(),monthArray)
        }

        binding.rvStatts.adapter=progressBar

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}