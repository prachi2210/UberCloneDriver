package com.wizebrain.adebdriver.ui.map.ride

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wizebrain.adebdriver.data.api.RetrofitBuilder
import com.wizebrain.adebdriver.R
import com.wizebrain.adebdriver.base.BaseFragment
import com.wizebrain.adebdriver.databinding.FragmentStartRideBinding
import com.wizebrain.adebdriver.extensions.loadImage
import com.wizebrain.adebdriver.ui.map.ride.listener.UserRideListener
import com.wizebrain.adebdriver.utils.Constants


class StartRideFragment : BaseFragment(), View.OnClickListener {
    private var type: String? = null
    private val binding get() = _binding!!
    private var _binding: FragmentStartRideBinding? = null
    private var callback: UserRideListener? = null
    var count = 0
    var clientRiderName = ""
    var clientRiderPhoto = ""
    var clientRiderPrice = ""
    var clientRiderRideId = ""
    var clientRiderPickupAddress = ""
    var clientRiderDropOffAddress = ""
    private val TAG: String = StartRideFragment::class.java.simpleName
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


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as UserRideListener

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartRideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRideStatus.setOnClickListener(this)
        binding.tvCloseFragment.setOnClickListener(this)
        binding.tvName.text = clientRiderName
        binding.tvCloseFragment.text=activity?.getString(R.string.start_your_trip)
        binding.tvPrice.text = "$ " + clientRiderPrice
        binding.tvPaymentType.text = "cash"
        if (clientRiderPhoto.isNotEmpty()) {
            binding.ivProfile.loadImage(RetrofitBuilder.BASE_URL + clientRiderPhoto)
        }

        binding.toAddress.text = clientRiderDropOffAddress
        binding.tvFromAddress.text = clientRiderPickupAddress


        Log.e(TAG,"on Oncreate Type ${type}")
        if(type.equals("0"))
        {
            binding.tvRideStatus.text = "Start trip"
        }

        else
        {
            binding.tvRideStatus.text = "End trip"
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onClick(v: View?) {
        when (v) {
            binding.tvRideStatus -> {
                Log.e(TAG,"on TvRide click Type ${type}")
                startTrip()
            }
            binding.tvCloseFragment -> {
                //always close on this position
                callback?.openClose(0)
            }


        }

    }

    private fun startTrip() {
        when (type) {
            "0" -> {
                type="1"
                binding.tvCloseFragment.text=activity?.getString(R.string.end_your_trip)
                binding.tvRideStatus.text = "End trip"
                callback?.onStartTrip(clientRiderRideId)
            }

            else -> {
                callback?.onEndTrip(clientRiderRideId)

            }
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
            StartRideFragment().apply {
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

}