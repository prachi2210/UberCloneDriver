package com.wizebrain.adebdriver.ui.map.response

data class GetBookingResponse(
    val msg: String?,
    val rideData: RideData?,
    val status: String?
)

data class RideData(
    val driverName: String?,
    val driverProfilePic: String?,
    val driverRef: String?,
    val fareAmount: String?,
    val id: String?,
    val userName: String?,
    val userProfilePic: String?,
    val userRef: String?
)