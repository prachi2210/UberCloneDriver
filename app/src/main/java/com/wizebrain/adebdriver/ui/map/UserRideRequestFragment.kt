package com.wizebrain.adebdriver.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.base.BaseFragment
import com.wizebrain.adebdriver.base.ViewModelProviderFactory
import com.wizebrain.adebdriver.data.api.ApiHelper
import com.wizebrain.adebdriver.databinding.FragmentUserRideRequestBinding
import com.wizebrain.adebdriver.ui.map.ride.listener.UserRideAcceptRejectListener


class UserRideRequestFragment(var callback: UserRideAcceptRejectListener) : BaseFragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentUserRideRequestBinding? = null
    private lateinit var viewModel: MapViewModel
    private val TAG: String = UserRideRequestFragment::class.java.simpleName



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserRideRequestBinding.inflate(inflater, container, false)
        return binding.root
    }








    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAccept.setOnClickListener {
            //1 for accept
            callback.onAcceptRejectClose(1)
        }

        binding.btnReject.setOnClickListener {
            callback.onAcceptRejectClose(0)
        }


    }




/*
    companion object {

        @JvmStatic
        fun newInstance(param: String) =
            UserRideRequestFragment().apply {
                arguments = Bundle().apply {
                    putString("type", param)

                }
            }
    }
*/


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

/*    override fun onAcceptReject(type: Int, position: Int) {

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

    }*/




}