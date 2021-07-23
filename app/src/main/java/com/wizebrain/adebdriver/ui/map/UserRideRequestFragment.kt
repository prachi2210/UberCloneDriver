package com.wizebrain.adebdriver.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wizebrain.adebdriver.data.api.RetrofitBuilder
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
    private var rideData: RideData? = null
    var clientRiderName = ""
    var clientRiderPhoto = ""
    var clientRiderPrice = ""
    var clientRiderRideId = ""
    var clientRiderPickupAddress = ""
    var clientRiderDropOffAddress = ""
    private var type: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as UserRideListener

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            clientRiderName = getString(Constants.NAME).toString().trim()
            clientRiderPhoto = getString(Constants.PHOTO).toString().trim()
            clientRiderPrice = getString(Constants.PRICE).toString().trim()
            clientRiderRideId = getString(Constants.RIDEID).toString().trim()
            clientRiderPickupAddress = getString(Constants.PICKUPADDFRESS).toString().trim()
            clientRiderDropOffAddress = getString(Constants.DROPOFFADDRESS).toString().trim()
            type = getString(Constants.TYPE).toString().trim()

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
        binding.tvName.text = clientRiderName
        binding.tvPrice.text = "$ ${clientRiderPrice}"

        if (clientRiderPhoto.isNotEmpty()) {
            binding.ivProfile.loadImage(RetrofitBuilder.BASE_URL + clientRiderPhoto)
        }

        binding.toAddress.text = clientRiderDropOffAddress
        binding.tvFromAddress.text = clientRiderPickupAddress




        binding.btnAccept.setOnClickListener {
            //1 for accept
            callback?.onAcceptRejectClose(1, clientRiderRideId)
        }

        binding.btnReject.setOnClickListener {
            callback?.onAcceptRejectClose(0, clientRiderRideId)
        }


    }


    companion object {
        @JvmStatic
        fun newInstance(
            clientRiderName: String,
            clientRiderPhoto: String,
            clientRiderPrice: String,
            clientRiderRideId: String,
            clientRiderPickupAddress: String,
            clientDropOffAddress: String, type: String
        ) =
            UserRideRequestFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.NAME, clientRiderName)
                    putString(Constants.PHOTO, clientRiderPhoto)
                    putString(Constants.PRICE, clientRiderPrice)
                    putString(Constants.RIDEID, clientRiderRideId)
                    putString(Constants.PICKUPADDFRESS, clientRiderPickupAddress)
                    putString(Constants.DROPOFFADDRESS, clientDropOffAddress)
                    putString(Constants.TYPE, type)
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}