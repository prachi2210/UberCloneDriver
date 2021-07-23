package com.wizebrain.adebdriver.ui.map.ride.listener

import com.wizebrain.adebdriver.ui.map.response.RideData
import com.wizebrain.adebdriver.ui.map.response.RideInfo

interface UserRideListener {
    fun onAcceptRejectClose(
        type: Int,
        rideId: String,
    )

    fun onStartTrip(rideId: String)
    fun onEndTrip(rideId: String)
    fun openClose(type: Int)

}