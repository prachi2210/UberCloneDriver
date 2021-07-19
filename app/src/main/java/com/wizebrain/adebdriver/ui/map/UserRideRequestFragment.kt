package com.wizebrain.adebdriver.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adebuser.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.base.BaseFragment
import com.wizebrain.adebdriver.databinding.FragmentUserRideRequestBinding
import com.wizebrain.adebdriver.extensions.loadImage
import com.wizebrain.adebdriver.ui.map.response.RideData
import com.wizebrain.adebdriver.ui.map.ride.listener.UserRideListener
import com.wizebrain.adebdriver.utils.Constants


class UserRideRequestFragment : BaseFragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentUserRideRequestBinding? = null
    private lateinit var viewModel: MapViewModel
    private val TAG: String = UserRideRequestFragment::class.java.simpleName
    private var callback: UserRideListener? = null
    private var userName: String? = null
    private var id: String? = null
    private var userProfilePic: String? = null
    private var fareAmount: String? = null
    private  var rideData:RideData?=null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as UserRideListener

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            rideData = getParcelable(Constants.RIDEDATA)
            userName = rideData?.userName
            id = rideData?.rideId
            userProfilePic = rideData?.userProfilePic
            fareAmount = rideData?.fareAmount
        }
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
        binding.tvName.text = userName
        binding.tvPrice.text = "$ $fareAmount"
        if (userProfilePic.toString().trim().isNotEmpty()) {
            binding.ivProfile.loadImage(RetrofitBuilder.BASE_URL + userProfilePic)
        }

        binding.btnAccept.setOnClickListener {
            //1 for accept
            callback?.onAcceptRejectClose(1, rideData)
        }

        binding.btnReject.setOnClickListener {
            callback?.onAcceptRejectClose(0, rideData)
        }


    }



    companion object {
        @JvmStatic
        fun newInstance(
            rideData: RideData?
        ) =
            UserRideRequestFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constants.RIDEDATA, rideData)
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}