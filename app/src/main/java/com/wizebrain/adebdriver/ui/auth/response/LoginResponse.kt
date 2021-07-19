package com.wizebrain.adebdriver.ui.auth.response

data class LoginResponse(
    val UserInfo: UserInfo?,
    val base_url: String?,
    val msg: String?,
    val status: String?,
    val varification: String?
)

data class UserInfo(
    val Is_Active: String?,
    val avgRatring: String?,
    val created_at: String?,
    val deviceToken: String?,
    val deviceType: String?,
    val drivingExperience: Any?,
    val drivingLicense: String?,
    val email: String?,
    val healthReport: String?,
    val id: String?,
    val is_booked: String?,
    val is_online: Any?,
    val latitude: String?,
    val loginStatus: String?,
    val longitude: String?,
    val name: String?,
    val notifications: String?,
    val password: String?,
    val personalId: String?,
    val phoneNumber: String?,
    val profilePic: String?,
    val rateCount: String?,
    val role: String?,
    val uniqueNumber: Any?,
    val updated_at: String?,
    val userRef: String?,
    val validationCode: String?
)