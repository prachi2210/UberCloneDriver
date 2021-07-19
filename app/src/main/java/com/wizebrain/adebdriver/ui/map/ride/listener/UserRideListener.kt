package com.wizebrain.adebdriver.ui.map.ride.listener

import com.wizebrain.adebdriver.ui.map.response.RideData
import com.wizebrain.adebdriver.ui.map.response.RideInfo

interface UserRideListener {
    fun onAcceptRejectClose(
        type: Int,
        rideData: RideData?,
    )
    fun onStartTrip(rideInfo: RideInfo?)
    fun onEndTrip(rideInfo: RideInfo?)
    fun openClose(type: Int)

}