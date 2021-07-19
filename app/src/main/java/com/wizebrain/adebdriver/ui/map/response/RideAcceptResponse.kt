package com.wizebrain.adebdriver.ui.map.response

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class RideAcceptResponse(
    val msg: String?,
    val rideInfo: RideInfo?,
    val status: String?
)
@Parcelize
data class RideInfo(
    val bookingStatus: String?,
    val carType: String?,
    val chooseType: String?,
    val coupon: String?,
    val created_at: String?,
    val driverName: String?,
    val driverProfilePic: String?,
    val driverRef: String?,
    val dropOffLat: String?,
    val dropOffLog: String?,
    val dropOffName: String?,
    val fareAmount: String?,
    val gearType: String?,
    val hourly: String?,
    val latitude: String?,
    val longitude: String?,
    val paymentMode: String?,
    val pickupLat: String?,
    val pickupLog: String?,
    val pickupName: String?,
    val rideId: String?,
    val scheduleTime: String?,
    val updated_at: String?,
    val userName: String?,
    val userProfilePic: String?,
    val userRef: String?
):Parcelable
