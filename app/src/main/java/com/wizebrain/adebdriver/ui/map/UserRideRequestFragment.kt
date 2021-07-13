package com.wizebrain.adebdriver.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wizebrain.adebdriver.base.BaseFragment
import com.wizebrain.adebdriver.databinding.FragmentUserRideRequestBinding
import com.wizebrain.adebdriver.ui.map.ride.listener.UserRideAcceptRejectListener


class UserRideRequestFragment(var callback: UserRideAcceptRejectListener) : BaseFragment() , UserRideRequestAdapter.UserCabRequest {
    private val binding get() = _binding!!
    private var _binding: FragmentUserRideRequestBinding? = null

    private val TAG: String = UserRideRequestFragment::class.java.simpleName

    private val userRideRequestAdapter: UserRideRequestAdapter by lazy {
        UserRideRequestAdapter(requireActivity(),this)
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

    override fun onAcceptReject(type: Int, position: Int) {

        Log.e(TAG,"acceptReject $type position=$position")

        when (type)

        {
            0->{
                callback.onAcceptRejectClose(type,position)

            }
            1->{
                callback.onAcceptRejectClose(type,position)
            }
        }

    }




}