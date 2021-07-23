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
    val carType: String?,
    val created_at: String?,
    val deviceToken: String?,
    val deviceType: String?,
    val drivingExperience: String?,
    val drivingLicense: DrivingLicense?,
    val email: String?,
    val gearType: String?,
    val healthReport: HealthReport?,
    val id: String?,
    val is_booked: Any?,
    val is_online: Any?,
    val latitude: String?,
    val loginStatus: String?,
    val longitude: String?,
    val name: String?,
    val notifications: String?,
    val password: String?,
    val personalId: PersonalId?,
    val phoneNumber: String?,
    val profilePic: String?,
    val pwd: String?,
    val rateCount: String?,
    val role: String?,
    val uniqueNumber: String?,
    val updated_at: String?,
    val userRef: String?,
    val validationCode: String?
)


data class PersonalId(
    val backSideUrl: String?,
    val created_at: String?,
    val docType: String?,
    val documentNumber: String?,
    val frontSideUrl: String?,
    val id: String?,
    val is_approved: String?,
    val updated_at: String?,
    val userRef: String?,
    val yourPicUrl: String?
)




data class DrivingLicense(
    val backSideUrl: String?,
    val created_at: String?,
    val docType: String?,
    val documentNumber: String?,
    val frontSideUrl: String?,
    val gearType: String?,
    val id: String?,
    val is_approved: String?,
    val updated_at: String?,
    val userRef: String?
)


data class HealthReport(
    val bloodgroup: String?,
    val created_at: String?,
    val dob: Any?,
    val docType: String?,
    val documentNumber: Any?,
    val frontSideUrl: String?,
    val id: String?,
    val is_approved: String?,
    val surgery: String?,
    val updated_at: String?,
    val userRef: String?
)